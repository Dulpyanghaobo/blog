package com.hab.blog.feature.v1.tarot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hab.blog.common.framework.manager.LLMService;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.carot.TarotCard;
import com.hab.blog.feature.v1.entities.carot.repository.TarotCardRepository;
import com.hab.blog.feature.v1.entities.repository.UserRepository;
import com.hab.blog.feature.v1.tarot.dto.TarotCardInterpretation;
import com.hab.blog.feature.v1.tarot.dto.TarotCardRequest;
import com.hab.blog.feature.v1.tarot.dto.TarotInterpretationRequest;
import com.hab.blog.feature.v1.tarot.dto.TarotInterpretationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarotReadingService {

    @Autowired
    private UserRepository userRepository;  // 获取用户信息

    @Autowired
    private TarotCardRepository tarotCardRepository;
    @Autowired
    @Qualifier("perplexityGptService")
    private LLMService llmService;  // 调用大语言模型（如ChatGPT）

    public TarotInterpretationResponse generateTarotInterpretation(TarotInterpretationRequest request) {
        // 1. 用户信息获取
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);

        // 2. 根据大数据推荐算法
        List<String> recommendationResults = getRecommendationsFromBigData(user);

        // 3. 塔罗牌本身的解读
        List<TarotCardInterpretation> cardInterpretations = interpretTarotCards(request.getTarotCards());

        // 4. 调用ChatGPT来生成对整个牌阵的综合解读
        String chatGptPrompt = buildChatGptPrompt(cardInterpretations, recommendationResults, user);
        String chatGptResponse = llmService.callModelSync(chatGptPrompt);

        // 5. 返回组合后的解读结果
        TarotInterpretationResponse response = new TarotInterpretationResponse();
        response.setCardInterpretations(cardInterpretations);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将 JSON 字符串解析为 JsonNode
            JsonNode rootNode = objectMapper.readTree(chatGptResponse);
            // 获取 choices 数组中的第一个元素的 message.content
            JsonNode messageContentNode = rootNode.path("choices").get(0).path("message").path("content");

            // 设置 OverallReading 为 message.content 的值
            if (messageContentNode.isTextual()) {
                response.setOverallReading(messageContentNode.asText());
            } else {
                response.setOverallReading("No valid response from GPT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setOverallReading("Error parsing GPT response");
        }
        return response;
    }

    private List<String> getRecommendationsFromBigData(Optional<User> user) {
        // 这里可以调用推荐算法服务，使用历史数据（如满意度）
        return List.of("User likes introspective readings", "Preference for Major Arcana");
    }

    private List<TarotCardInterpretation> interpretTarotCards(List<TarotCardRequest> tarotCards) {
        return tarotCards.stream().map(card -> {
            String position = card.isUpright() ? "upright" : "reversed";
            Optional<TarotCard> tarotCard = tarotCardRepository.findById(card.getId());
            String interpretation = card.isUpright() ? tarotCard.orElse(null).getUprightMeaning() : tarotCard.orElse(null).getReversedMeaning();
            return new TarotCardInterpretation(card.getName(), position, interpretation);
        }).toList();
    }

    private String buildChatGptPrompt(List<TarotCardInterpretation> cardInterpretations, List<String> recommendations, Optional<User> user) {
        // 根据牌阵的解读和大数据推荐构建一个Prompt传给ChatGPT
        StringBuilder prompt = new StringBuilder();
        prompt.append("The user has drawn the following tarot cards:\n");
        for (TarotCardInterpretation interpretation : cardInterpretations) {
            prompt.append("- Card: ").append(interpretation.getCardName()).append("\n");
            prompt.append("  Position: ").append(interpretation.getPosition()).append("\n");
            prompt.append("  Interpretation: ").append(interpretation.getInterpretation()).append("\n");
        }
        prompt.append("The user has the following characteristics based on past data:\n");
        for (String recommendation : recommendations) {
            prompt.append("- ").append(recommendation).append("\n");
        }
        prompt.append("Please provide an overall tarot reading based on the above information.");
        return prompt.toString();
    }
}


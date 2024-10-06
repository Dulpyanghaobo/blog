package com.hab.blog.common.framework.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hab.blog.common.framework.manager.LLMService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@Service
public class PerplexityGptService implements LLMService {
    private final WebClient webClient;

    @Value("${chat.perplexity.apiKey}")
    private String API_KEY;

    public PerplexityGptService(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
                .proxy(proxy -> proxy
                        .type(ProxyProvider.Proxy.HTTP)
                        .host("127.0.0.1")  // 代理服务器地址
                        .port(7890));
        this.webClient = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient)).baseUrl("https://api.perplexity.ai")
                .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }

    @Override
    public String callModelSync(String prompt) {
        // 同步调用Perplexity GPT
        return webClient.post()
                .uri("chat/completions")
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(buildRequestBody("用户抽到了以下塔罗牌：\n" +
                        "- 牌： 正义\n" +
                        "  位置：反转\n" +
                        "  解释： 不公平、缺乏责任感、不诚实\n" +
                        "- 牌：皇帝 皇帝\n" +
                        "  位置：正位\n" +
                        "  解释：权威、结构、控制、父亲的形象： 权威、结构、控制、父亲的形象\n" +
                        "- 卡牌：隐士 隐士\n" +
                        "  位置：直立\n" +
                        "  解读：隐士 灵魂探索、反省、独处、内在指引\n" +
                        "根据过去的数据，用户具有以下特征：\n" +
                        "- 用户喜欢内省式的解读\n" +
                        "- 偏好大阿卡纳\n" +
                        "请根据以上信息提供一个总体塔罗占卜。"))
                .retrieve()
                .bodyToMono(String.class)
                .block();  // 同步阻塞获取结果
    }

    @Override
    public CompletableFuture<String> callModelAsync(String prompt) {
        // 异步调用Perplexity GPT
        return webClient.post()
                .uri("completions")
                .header("Authorization", "Bearer " + API_KEY)
                .bodyValue(buildRequestBody("作为一位专业的塔罗师，你正在为一位求问者进行塔罗牌解读。求问者的主要问题是关于当前感情关系，特别是想了解伴侣在这段感情中的问题，以及感情的未来走向。使用的牌阵是三张牌牌阵，分别代表过去、现在和未来。\n" +
                        "\n" +
                        "以下是求问者抽到的塔罗牌：\n" +
                        "- 过去：正义（逆位）——代表过去在感情中不公平或责任缺失的情况。\n" +
                        "- 现在：皇帝（正位）——代表现在感情中的权威、控制或结构化。\n" +
                        "- 未来：隐士（正位）——代表未来的自我反省、内心探索或独处的趋势。\n" +
                        "\n" +
                        "请根据以上信息提供以下解读：\n" +
                        "1. 伴侣在当前感情中的问题是什么？他们是否存在责任感或诚信方面的问题？\n" +
                        "2. 这段感情的未来走向如何？求问者应该如何应对这段感情？\n" +
                        "3. 提供一个简洁的总结，包括对求问者的建议和这段感情的最终结果。\n" +
                        "\n" +
                        "请以专业、简洁的语气给出直接的解答，帮助求问者理解他们当前的感情状态并获得有用的建议。\n"))
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();  // 返回异步CompletableFuture
    }

    // 构建请求体
    private String buildRequestBody(String prompt) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.1-sonar-small-128k-online");  // 可以根据需要选择模型

        // 构建 messages 结构
        List<Map<String, String>> messages = new ArrayList<>();

        // 添加 system 角色
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "你是塔罗牌专家。用塔罗占卜的见解回答问题。");
        messages.add(systemMessage);
        Map<String, String> languagemessage = new HashMap<>();

        languagemessage.put("role", "system");
        languagemessage.put("content", "请用中文回答");
        messages.add(languagemessage);

        // 添加 user 角色
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);  // 使用传入的 prompt 作为用户输入
        messages.add(userMessage);

        body.put("messages", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

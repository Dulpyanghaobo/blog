package com.hab.blog.feature.v1.tarot.service;

import com.hab.blog.feature.v1.entities.carot.TarotCard;
import com.hab.blog.feature.v1.entities.carot.repository.TarotCardRepository;
import com.hab.blog.feature.v1.tarot.dto.TarotCardResponse;
import com.hab.blog.feature.v1.tarot.dto.TarotDrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TarotCardService {

    @Autowired
    private TarotCardRepository tarotCardRepository;

    private final Random random = new Random();

    /**
     * 抽取塔罗牌并返回每张牌的详细信息
     * @param request 客户端传入的塔罗牌序号
     * @return 返回塔罗牌信息
     */
    public List<TarotCardResponse> drawTarotCards(TarotDrawRequest request) {
        List<TarotCardResponse> responses = new ArrayList<>();

        for (Long cardNumber : request.getCardNumbers()) {

            TarotCard card = tarotCardRepository.findById(cardNumber)
                    .orElseThrow(() -> new RuntimeException("Card not found for number: " + cardNumber));

            boolean isUpright = random.nextBoolean();

            TarotCardResponse response = new TarotCardResponse();
            response.setId(card.getId());
            response.setName(card.getName());
            response.setNumber(card.getNumber());
            response.setArcanaType(card.getArcanaType());
            response.setSuit(card.getSuit());
            response.setArchetype(card.getArchetype());
            response.setAttributes(card.getAttributes());
            response.setSymbolism(card.getSymbolism());
            response.setImageUrl(card.getImageUrl());
            response.setQuestions(card.getQuestions());
            response.setAstrologicalSign(card.getAstrologicalSign());
            response.setElement(card.getElement());
            response.setChakra(card.getChakra());
            response.setNumerology(card.getNumerology());
            response.setKeywords(card.getKeywords());
            response.setUpright(isUpright);

            // 根据正位或逆位，设置相应的 meaning 和 advice
            if (isUpright) {
                response.setMeaning(card.getUprightMeaning());
                response.setAdvice(card.getUprightAdvice());
            } else {
                response.setMeaning(card.getReversedMeaning());
                response.setAdvice(card.getReversedAdvice());
            }

            responses.add(response);
        }

        return responses;
    }
}

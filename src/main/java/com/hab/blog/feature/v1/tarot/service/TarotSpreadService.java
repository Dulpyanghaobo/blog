package com.hab.blog.feature.v1.tarot.service;

import com.hab.blog.feature.v1.entities.carot.TarotSpread;
import com.hab.blog.feature.v1.entities.carot.repository.TarotSpreadRepository;
import com.hab.blog.feature.v1.tarot.dto.TarotSpreadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarotSpreadService {

    @Autowired
    private TarotSpreadRepository tarotSpreadRepository;

    // 获取所有塔罗牌阵并返回DTO
    public List<TarotSpreadResponse> getAllTarotSpreads() {
        List<TarotSpread> spreads = tarotSpreadRepository.findAll();
        return spreads.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 根据ID获取塔罗牌阵详情并返回DTO
    public TarotSpreadResponse getTarotSpreadById(Long id) {
        TarotSpread spread = tarotSpreadRepository.findById(id).orElse(null);
        return spread != null ? mapToResponse(spread) : null;
    }

    // 将 TarotSpread 实体映射为 TarotSpreadResponse
    private TarotSpreadResponse mapToResponse(TarotSpread spread) {
        TarotSpreadResponse response = new TarotSpreadResponse();
        response.setId(spread.getId());
        response.setName(spread.getName());
        response.setSpreadType(String.valueOf(spread.getSpreadType()));
        response.setPaid(spread.isPaid());
        response.setPopular(spread.isPopular());
        response.setTags(spread.getTags());
        response.setTestCount(spread.getTestCount());
        response.setLikeCount(spread.getLikeCount());
        response.setImageLarge(spread.getImageLarge());
        response.setImageSmall(spread.getImageSmall());
        response.setDescription(spread.getDescription());
        response.setRelatedQuestions(spread.getRelatedQuestions());
        response.setCreatedAt(spread.getCreatedAt());
        response.setUpdatedAt(spread.getUpdatedAt());
        return response;
    }
}

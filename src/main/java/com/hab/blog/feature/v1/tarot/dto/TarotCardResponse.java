package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;

@Data
public class TarotCardResponse {
    private Long id;
    private String name;
    private Integer number;
    private String arcanaType;
    private String suit;
    private String archetype;
    private String attributes;
    private String symbolism;
    private String meaning;   // 根据正位或逆位，返回uprightMeaning或reversedMeaning
    private String advice;    // 根据正位或逆位，返回uprightAdvice或reversedAdvice
    private boolean isUpright; // 是否是正位
    private String imageUrl;
    private String questions;
    private String astrologicalSign;
    private String element;
    private String chakra;
    private String numerology;
    private String keywords;
}

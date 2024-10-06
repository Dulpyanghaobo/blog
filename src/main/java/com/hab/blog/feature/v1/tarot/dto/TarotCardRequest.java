package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;

@Data
public class TarotCardRequest {

    private Long id;  // TarotCard id (关联数据库中的塔罗牌)
    private String name;
    private Integer number;
    private String arcanaType;
    private String suit;
    private String archetype;
    private String attributes;
    private String symbolism;
    private String advice;
    private String imageUrl;
    private boolean upright;  // 用户上传的正位/逆位信息

}


package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;

import java.util.List;

@Data
public class TarotDrawRequest {
    private List<Long> cardNumbers;  // 客户端传入的塔罗牌序号

}

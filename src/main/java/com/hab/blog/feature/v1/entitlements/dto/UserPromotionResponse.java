package com.hab.blog.feature.v1.entitlements.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserPromotionResponse {
    private String promotionName;
    private boolean isRedeemed;
    private Instant redeemedAt;
}

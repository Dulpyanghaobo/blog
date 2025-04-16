package com.hab.blog.feature.v1.entitlements.dto;

import com.hab.blog.feature.v1.entities.entitlements.PromotionType;
import com.hab.blog.feature.v1.entities.entitlements.TargetUserGroup;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PromotionResponse {
    private String promotionName;
    private PromotionType promotionType;
    private BigDecimal discountAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private TargetUserGroup targetUserGroup;
}

package com.hab.blog.feature.v1.entitlements.dto;

import lombok.Data;

@Data
public class EntitlementPurchaseRequest {
    private String entitlementType; // 权益类型，如存储空间或观看次数
    private int quantity; // 购买的数量或额度
}

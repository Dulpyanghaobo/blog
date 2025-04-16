package com.hab.blog.feature.v1.entitlements.dto;

import lombok.Data;

@Data
public class SubscriptionUpgradeRequest {
    private String subscriptionType; // 如 monthly, yearly
    private boolean autoRenew;
}

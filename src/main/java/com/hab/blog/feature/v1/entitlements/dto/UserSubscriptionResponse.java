package com.hab.blog.feature.v1.entitlements.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSubscriptionResponse {
    private String subscriptionType;
    private String subscriptionStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAutoRenew;
}

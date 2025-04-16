package com.hab.blog.feature.v1.entitlements.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserEntitlementResponse {
    private String entitlementType;
    private int maxLimit;
    private int usedCount;
    private int remainingCount;
    private LocalDate startDate;
    private LocalDate endDate;
}


package com.hab.blog.feature.v1.entities.entitlements;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String planName;  // 订阅方案名称

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false)
    private SubscriptionType planType;  // 订阅类型（如月付、年付）

    @Column(name = "price", nullable = false)
    private BigDecimal price;  // 订阅费用

    @Column(name = "description")
    private String description;  // 订阅方案描述

    @Column(name = "max_entitlements")
    private String maxEntitlements;  // JSON格式的权益定义

    @Column(name = "is_active", nullable = false)
    private boolean isActive;  // 是否是有效方案

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();  // 创建时间

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();  // 更新时间
}

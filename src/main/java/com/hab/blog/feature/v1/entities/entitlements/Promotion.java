package com.hab.blog.feature.v1.entities.entitlements;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promotion_name", nullable = false)
    private String promotionName;  // 促销活动名称

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type", nullable = false)
    private PromotionType promotionType;  // 促销类型（如折扣或免费试用）

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;  // 折扣金额

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;  // 活动开始日期

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;  // 活动结束日期

    @Enumerated(EnumType.STRING)
    @Column(name = "target_user_group", nullable = false)
    private TargetUserGroup targetUserGroup;  // 活动目标用户群体

    @Column(name = "description")
    private String description;  // 活动描述

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();  // 创建时间

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();  // 更新时间
}

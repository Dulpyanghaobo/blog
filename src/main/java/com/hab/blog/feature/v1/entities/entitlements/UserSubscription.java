package com.hab.blog.feature.v1.entities.entitlements;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "user_subscriptions")
@Data
@NoArgsConstructor
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;  // 关联用户

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type", nullable = false)
    private SubscriptionType subscriptionType;  // 订阅类型（如月付、年付）

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;  // 订阅状态

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;  // 订阅开始日期

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;  // 订阅结束日期

    @Column(name = "is_auto_renew", nullable = false)
    private boolean isAutoRenew;  // 是否自动续费

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();  // 创建时间

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();  // 更新时间
}


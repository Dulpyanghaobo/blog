package com.hab.blog.feature.v1.entities.entitlements;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "user_entitlements")
@Data
@NoArgsConstructor
public class UserEntitlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;  // 关联用户

    @Column(name = "entitlement_type", nullable = false)
    private String entitlementType;  // 权益类型（如存储空间、观看次数等）

    @Column(name = "max_limit", nullable = false)
    private int maxLimit;  // 最大限额

    @Column(name = "used_count", nullable = false)
    private int usedCount;  // 已使用的权益数量

    @Column(name = "remaining_count", nullable = false)
    private int remainingCount;  // 剩余的权益数量

    @Column(name = "start_date")
    private LocalDate startDate;  // 权益的开始日期（如限时权益）

    @Column(name = "end_date")
    private LocalDate endDate;  // 权益的结束日期

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();  // 创建时间

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();  // 更新时间
}


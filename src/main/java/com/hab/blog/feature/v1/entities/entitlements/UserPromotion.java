package com.hab.blog.feature.v1.entities.entitlements;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_promotions")
@Data
@NoArgsConstructor
public class UserPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;  // 关联用户

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;  // 关联促销活动

    @Column(name = "redeemed_at", nullable = false)
    private Instant redeemedAt = Instant.now();  // 用户参与促销的时间

    @Column(name = "is_redeemed", nullable = false)
    private boolean isRedeemed;  // 是否已使用促销权益
}

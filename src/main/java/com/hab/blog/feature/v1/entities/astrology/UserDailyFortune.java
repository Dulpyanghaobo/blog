package com.hab.blog.feature.v1.entities.astrology;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_daily_fortune")
@Data
public class UserDailyFortune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // 关联到用户表
    private User user;

    @Column(name = "overall_score")  // 综合评分
    private Integer overallScore;

    @Column(name = "love_score")  // 爱情评分
    private Integer loveScore;

    @Column(name = "wealth_score")  // 财富评分
    private Integer wealthScore;

    @Column(name = "career_score")  // 事业评分
    private Integer careerScore;

    @Column(name = "encouragement_message", columnDefinition = "TEXT")  // 每日勉励语
    private String encouragementMessage;

    @Column(name = "is_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")  // 模块是否锁定
    private Boolean isLocked = false;

    @Column(name = "fortune_date", nullable = false)  // 运势日期
    private LocalDate fortuneDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 无参构造函数
    public UserDailyFortune() {
        this.createdAt = LocalDateTime.now();  // 默认创建时间
    }
}


package com.hab.blog.feature.v1.entities.astrology;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "user_astrology")
@Data
public class UserAstrology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 多个用户星象可以关联同一个星象事件
    @JoinColumn(name = "astrology_event_id", nullable = false)  // 关联 astrology_events 表
    private AstrologyEvents astrologyEvent;

    @ManyToOne(fetch = FetchType.LAZY)  // 多个星象解读可以关联同一个用户
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;

    @Column(name = "personal_interpretation", columnDefinition = "TEXT")  // 个人解读
    private String personalInterpretation;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
}

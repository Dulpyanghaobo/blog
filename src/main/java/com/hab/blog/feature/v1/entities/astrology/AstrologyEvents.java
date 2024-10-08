package com.hab.blog.feature.v1.entities.astrology;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "astrology_events")
public class AstrologyEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增主键
    private Long id;

    @Column(name = "event_date", nullable = false)  // 日期字段
    private LocalDate eventDate;

    @Column(name = "planet_position", length = 255)  // 行星运行位置
    private String planetPosition;

    @Column(name = "zodiac_change", length = 255)  // 星座变动
    private String zodiacChange;

    @Column(name = "description", columnDefinition = "TEXT")  // 详细描述
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
}

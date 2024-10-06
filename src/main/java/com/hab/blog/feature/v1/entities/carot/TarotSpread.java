package com.hab.blog.feature.v1.entities.carot;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarot_spreads")
@Data
public class TarotSpread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "spread_type", nullable = false)
    private SpreadType spreadType;

    @Column(name = "is_paid", nullable = false, columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean isPaid;

    @Column(name = "is_popular", nullable = false, columnDefinition = "BIT(1) DEFAULT b'0'")
    private boolean isPopular;

    private String tags;

    @Column(name = "test_count", columnDefinition = "BIGINT DEFAULT 0")
    private Long testCount;

    @Column(name = "like_count", columnDefinition = "BIGINT DEFAULT 0")
    private Long likeCount;

    @Column(name = "image_large")
    private String imageLarge;

    @Column(name = "image_small")
    private String imageSmall;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "related_questions", columnDefinition = "TEXT")
    private String relatedQuestions;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}

enum SpreadType {
    basic, advanced
}

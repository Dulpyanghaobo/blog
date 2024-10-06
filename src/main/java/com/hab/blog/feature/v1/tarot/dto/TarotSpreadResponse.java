package com.hab.blog.feature.v1.tarot.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TarotSpreadResponse {
    private Long id;
    private String name;
    private String spreadType;
    private boolean isPaid;
    private boolean isPopular;
    private String tags;
    private Long testCount;
    private Long likeCount;
    private String imageLarge;
    private String imageSmall;
    private String description;
    private String relatedQuestions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

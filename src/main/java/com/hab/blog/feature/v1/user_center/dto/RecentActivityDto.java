package com.hab.blog.feature.v1.user_center.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecentActivityDto {
    private String activityType;
    private LocalDateTime timestamp;
}

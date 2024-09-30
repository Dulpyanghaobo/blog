package com.hab.blog.feature.v1.User.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecentActivityDto {
    private String activityType;
    private LocalDateTime timestamp;
}

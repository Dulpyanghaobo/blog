package com.hab.blog.feature.v1.moods.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MoodRequest {
    private Long userId;
    private LocalDate date;
    private Integer moodLevel;
}
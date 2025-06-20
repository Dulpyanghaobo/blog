package com.hab.blog.api.v1.moods.Entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MoodRequest {
    private Long userId;
    private LocalDate date;
    private Integer moodLevel;
}
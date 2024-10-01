package com.hab.blog.feature.v1.user_center.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LinkedAccountDto {
    private String accountType;
    private LocalDate linkedDate;
}

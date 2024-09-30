package com.hab.blog.feature.v1.auth.Dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;

}
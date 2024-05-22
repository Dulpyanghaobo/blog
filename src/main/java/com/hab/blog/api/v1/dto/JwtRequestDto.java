package com.hab.blog.api.v1.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;

}
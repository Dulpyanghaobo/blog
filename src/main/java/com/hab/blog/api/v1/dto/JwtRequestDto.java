package com.hab.blog.api.v1.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;

}
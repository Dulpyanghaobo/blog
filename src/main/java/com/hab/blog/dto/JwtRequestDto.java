package com.hab.blog.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;

}
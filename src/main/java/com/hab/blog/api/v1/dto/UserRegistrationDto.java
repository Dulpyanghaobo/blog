package com.hab.blog.api.v1.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRegistrationDto {
    private String userName;
    private String avatar;
    private String email;
    private String password;
    private List<String> roles; // 添加角色字段
    // getters and setters
}

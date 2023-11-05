package com.hab.blog.dto;

import lombok.Data;

@Data
public class UserDto {
    private String displayName;
    private String avatar;
    private String email;
    private String password;

    // Standard getters and setters
}

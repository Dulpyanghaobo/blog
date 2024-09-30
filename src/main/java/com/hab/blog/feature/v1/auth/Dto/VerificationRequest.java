package com.hab.blog.feature.v1.auth.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationRequest {
    String token;
    Long userId;
    String email;
}

package com.hab.blog.api.v1.auth.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationRequest {
    String token;
    Long userId;
    String email;

}

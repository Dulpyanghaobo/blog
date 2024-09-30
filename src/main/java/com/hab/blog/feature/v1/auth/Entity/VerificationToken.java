package com.hab.blog.feature.v1.auth.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    private static final int EXPIRATION_TIME = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "token_type")
    private TokenType tokenType;

    @Column(name = "email")
    private String email;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public VerificationToken(String token, TokenType tokenType, String email, Long userId) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.userId = userId;
        this.expiryDate = calculateExpiryDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token, TokenType tokenType, String email) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.expiryDate = calculateExpiryDate(EXPIRATION_TIME);
    }

    private Instant calculateExpiryDate(int expiryTimeInHours) {
        return Instant.now().plusSeconds(expiryTimeInHours * 3600);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }

    public enum TokenType {
        VERIFY_EMAIL,
        RESET_PASSWORD
    }
}

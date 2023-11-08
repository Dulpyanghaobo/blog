package com.hab.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data // Lombok注解，用于自动生成getter、setter、equals、hashCode和toString方法
@NoArgsConstructor // Lombok注解，用于自动生成无参数构造函数
@Entity // JPA注解，表示这是一个数据库实体
@Table(name = "verification_tokens") // JPA注解，指定数据库中的表名
public class VerificationToken {

    private static final int EXPIRATION_TIME = 24; // 令牌过期时间，例如24小时

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION_TIME);
    }

    private Instant calculateExpiryDate(int expiryTimeInHours) {
        return Instant.now().plusSeconds(expiryTimeInHours * 3600);
    }

    // 检查令牌是否过期
    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }
}

package com.hab.blog.feature.v1.auth.Entity;

import com.hab.blog.feature.v1.model.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String avatar;
    private String bio;
    private boolean disabled;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = true)
    private Instant registeredAt;

    private boolean twoFactorAuthEnabled;

    @Column(nullable = false)
    private String userName;

    // 新增的 openid 字段，微信用户的唯一标识
    @Column(unique = true)
    private String openid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}

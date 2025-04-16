package com.hab.blog.feature.v1.entities.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_linked_accounts")
@Data
@NoArgsConstructor
public class UserLinkedAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;

    private String accountType;    // 绑定的账户类型，例如 "WeChat"、"Email" 等
    private LocalDateTime linkedDate; // 绑定时间
}


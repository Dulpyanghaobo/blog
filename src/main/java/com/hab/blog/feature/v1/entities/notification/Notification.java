package com.hab.blog.feature.v1.entities.notification;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 与用户的多对一关系

    @Column(name = "type", length = 50, nullable = false)
    private String type;  // 通知类型 (如 COMMENT, LIKE, SYSTEM_UPDATE)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;  // 通知内容

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;  // 通知时间

    @Column(name = "is_read", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRead = false;  // 是否已读，默认未读
}


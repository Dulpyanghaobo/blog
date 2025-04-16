package com.hab.blog.feature.v1.entities.notification;

import com.hab.blog.feature.v1.entities.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName", nullable = false)
    private User user;  // 与用户的多对一关系

    @Column(name = "title", length = 255, nullable = false)
    private String title;  // 消息标题

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;  // 消息内容

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;  // 消息时间

    @Column(name = "is_read", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRead = false;  // 是否已读，默认未读
}

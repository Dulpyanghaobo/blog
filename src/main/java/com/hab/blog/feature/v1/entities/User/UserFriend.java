package com.hab.blog.feature.v1.entities.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_friends")
@Data
@NoArgsConstructor
public class UserFriend {

    @EmbeddedId
    private UserFriendId id = new UserFriendId();  // 复合主键

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;  // 当前用户

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private User friend;  // 好友

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status = FriendshipStatus.PENDING;  // 好友关系状态

    @Column(name = "added_at", nullable = false)
    private Instant addedAt = Instant.now();  // 使用 Java 自动设置当前时间
}

package com.hab.blog.feature.v1.entities.notification.respository;

import com.hab.blog.feature.v1.entities.notification.Message;
import com.hab.blog.feature.v1.entities.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findUnreadNotificationsByUserId(Long userId);
    int countUnreadNotificationsByUserId(Long userId);
}


package com.hab.blog.feature.v1.notification;

import com.hab.blog.feature.v1.entities.notification.Message;
import com.hab.blog.feature.v1.entities.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 获取未读消息和通知
    @GetMapping("/unread")
    public ResponseEntity<Map<String, Object>> getUnreadNotificationsAndMessages() {

        List<Message> unreadMessages = notificationService.getUnreadMessages();
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications();

        Map<String, Object> response = new HashMap<>();
        response.put("unreadMessages", unreadMessages);
        response.put("unreadNotifications", unreadNotifications);

        return ResponseEntity.ok(response);
    }

    // 获取未读消息和通知计数
    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Integer>> getUnreadCount() {
        int unreadMessagesCount = notificationService.getUnreadMessagesCount();
        int unreadNotificationsCount = notificationService.getUnreadNotificationsCount();

        Map<String, Integer> response = new HashMap<>();
        response.put("unreadMessagesCount", unreadMessagesCount);
        response.put("unreadNotificationsCount", unreadNotificationsCount);

        return ResponseEntity.ok(response);
    }
}

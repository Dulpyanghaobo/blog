package com.hab.blog.feature.v1.notification;

import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.notification.Message;
import com.hab.blog.feature.v1.entities.notification.Notification;
import com.hab.blog.feature.v1.entities.notification.respository.MessageRepository;
import com.hab.blog.feature.v1.entities.notification.respository.NotificationRepository;
import com.hab.blog.feature.v1.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // 获取用户的未读消息
    public List<Message> getUnreadMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);
        return messageRepository.findUnreadMessagesByUserId(user.orElseThrow().getId());
    }

    // 获取用户的未读通知
    public List<Notification> getUnreadNotifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);
        return notificationRepository.findUnreadNotificationsByUserId(user.orElseThrow().getId());
    }

    // 获取未读消息和通知的计数
    public int getUnreadMessagesCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);
        return messageRepository.countUnreadMessagesByUserId(user.orElseThrow().getId());
    }

    public int getUnreadNotificationsCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);
        return notificationRepository.countUnreadNotificationsByUserId(user.orElseThrow().getId());
    }
}


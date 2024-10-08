package com.hab.blog.feature.v1.entities.notification.respository;

import com.hab.blog.feature.v1.entities.notification.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findUnreadMessagesByUserId(Long userId);

    int countUnreadMessagesByUserId(Long userId);
}

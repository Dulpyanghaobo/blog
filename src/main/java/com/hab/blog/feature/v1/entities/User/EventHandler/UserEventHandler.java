package com.hab.blog.feature.v1.entities.User.EventHandler;

import com.hab.blog.feature.v1.entities.User.User;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    @HandleAfterCreate
    public void handlePersonCreate(User user) {
        // 这里可以添加你希望在 User 创建之后执行的逻辑
    }
}


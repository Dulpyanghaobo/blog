package com.hab.blog.feature.v1.User;

import com.hab.blog.feature.v1.User.Dto.UserProfileDto;
import com.hab.blog.feature.v1.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        // 从 SecurityContext 中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 从 Authentication 中获取用户名（即 JWT 中的用户名）
        String username = authentication.getName();

        // 调用服务层获取用户详细信息
        UserProfileDto userProfile = userService.getUserProfile(username);

        return ResponseEntity.ok(userProfile);
    }
}


package com.hab.blog.feature.v1.User;

import com.hab.blog.feature.v1.User.Dto.UserProfileDto;
import com.hab.blog.feature.v1.User.Dto.UserProfileUpdateDto;
import com.hab.blog.feature.v1.User.Service.UserService;
import com.hab.blog.feature.v1.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    // 更新用户资料的接口
    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserProfileUpdateDto profileUpdateDto) {
        // 从 SecurityContext 中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.updateUserProfile(username, profileUpdateDto);

        return ResponseEntity.ok("Profile updated successfully");
    }
}


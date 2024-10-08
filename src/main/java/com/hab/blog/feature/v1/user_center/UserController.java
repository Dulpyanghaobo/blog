package com.hab.blog.feature.v1.user_center;

import com.hab.blog.feature.v1.user_center.dto.UserProfileDto;
import com.hab.blog.feature.v1.user_center.dto.UserProfileUpdateDto;
import com.hab.blog.feature.v1.user_center.Service.UserService;
import com.hab.blog.feature.v1.user_center.dto.UserSettingsDto;
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

    @GetMapping("/settings")
    public ResponseEntity<UserSettingsDto> getUserSettings() {
        // 从 SecurityContext 中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 调用服务层获取用户设置
        UserSettingsDto userSettings = userService.getUserSettings(username);

        return ResponseEntity.ok(userSettings);
    }

    // 检查用户是否完成新手教程
    @GetMapping("/tutorial/status")
    public ResponseEntity<Boolean> hasCompletedTutorial() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean hasCompleted = userService.hasCompletedTutorial(username);
        return ResponseEntity.ok(hasCompleted);
    }

    // 标记用户完成新手教程
    @PostMapping("/tutorial/complete")
    public ResponseEntity<String> completeTutorial() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.completeTutorial(username);
        return ResponseEntity.ok("Tutorial completed");
    }

    @PutMapping("/settings")
    public ResponseEntity<String> updateUserSettings(@Valid @RequestBody UserSettingsDto userSettingsDto) {
        // 从 SecurityContext 中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // 调用服务层更新用户设置
        userService.updateUserSettings(username, userSettingsDto);

        return ResponseEntity.ok("Settings updated successfully");
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
package com.hab.blog.feature.v1.User;

import com.hab.blog.feature.v1.User.Dto.UserProfileDto;
import com.hab.blog.feature.v1.User.Dto.UserProfileUpdateDto;
import com.hab.blog.feature.v1.User.Service.UserService;
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
//    绑定手机号	POST	/api/v1/user/bind/phone	绑定用户手机号
    // 检查是否绑定了手机号
    // 如果绑定了 先调用api解绑手机号
    // 否则直接绑定手机号
    // 绑定手机号
        // 请求 api 获取验证码
        // 用户输入验证码校验， 校验成功
            // 绑定成功
            // 否则绑定失败

//    解绑手机号	POST	/api/v1/user/unbind/phone	解绑用户手机号
        // 检查用户是否绑定了手机号，如果绑定了则调用发送验证码api解绑
        // 用户输入验证码， 调用验证解绑api然后解除绑定， user表里面的手机号为null
}

// springboot + gradle + JDK20
package com.hab.blog.Controller;

import com.hab.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.hab.blog.model.*;
import com.hab.blog.dto.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User newUser = userService.createUser(
                userDto.getDisplayName(),
                userDto.getAvatar(),
                userDto.getEmail(),
                userDto.getPassword()
        );
        return ResponseEntity.ok(newUser);
    }
    // 这里添加处理HTTP请求的方法，比如获取用户列表、创建新用户等
}


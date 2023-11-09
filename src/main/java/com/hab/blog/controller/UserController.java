package com.hab.blog.controller;

import com.hab.blog.service.UserService;
import com.hab.blog.model.VerificationToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hab.blog.model.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/activate")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null || verificationToken.isExpired()) {
            // Token无效或已过期
            return new ResponseEntity<>("Token is invalid or expired", HttpStatus.BAD_REQUEST);
        }

        User user = verificationToken.getUser();
        user.setDisabled(false); // 或者其他标记为已验证的逻辑
        userService.updateUser(user);
        return new ResponseEntity<>("User verified successfully!", HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

    // 这里添加处理HTTP请求的方法，比如获取用户列表、创建新用户等
}


package com.hab.blog.controller;

import com.hab.blog.dto.JwtRequestDto;
import com.hab.blog.dto.UserRegistrationDto;
import com.hab.blog.model.User;
import com.hab.blog.model.VerificationToken;
import com.hab.blog.service.UserService;
import com.hab.blog.utility.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDto user) {
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

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenProvider.createToken(authentication);

        return ResponseEntity.ok(token);
    }
    // 这里添加处理HTTP请求的方法，比如获取用户列表、创建新用户等
}


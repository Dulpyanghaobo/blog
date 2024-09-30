package com.hab.blog.feature.v1.auth;

import com.hab.blog.feature.v1.auth.Entity.User;
import com.hab.blog.feature.v1.auth.Entity.VerificationRequest;
import com.hab.blog.feature.v1.auth.Entity.VerificationToken;
import com.hab.blog.feature.v1.auth.Service.UserService;
import com.hab.blog.feature.v1.dto.JwtRequestDto;
import com.hab.blog.feature.v1.auth.Entity.UserRegistrationDto;
import com.hab.blog.feature.v1.response.ApiResponse;
import com.hab.blog.feature.v1.response.exception.AlreadyExistsException;
import com.hab.blog.feature.v1.utility.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest) throws Exception {
        Optional<User> user = userService.findUsersByEmail(authenticationRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(404, "User not found", "User not found"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.orElse(null).getUserName(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenProvider.createToken(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<String>(201, "Generate token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody UserRegistrationDto user) throws Exception {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success( String.valueOf(newUser.getId())));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(409, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "Failed to register user", null));
        }
    }

    @PostMapping("/activity/verify")
    public ResponseEntity<?> confirmRegistration(@RequestBody VerificationRequest request) {
        String token = request.getToken();
        Long userId = request.getUserId();

        // 检查用户是否存在
        Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(404, "User not found", "User not found"));
        }

        // 检查验证码是否存在
        Optional<VerificationToken> optionalToken = userService.findByUserIdAndToken(token, userId);
        if (!optionalToken.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(409, "Invalid verification token", "Invalid verification token"));
        }

        VerificationToken verificationToken = optionalToken.get();

        // 检查验证码是否过期
        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification token has expired");
        }

        // 验证码正确,更新用户状态
        User user = optionalUser.get();
        user.setDisabled(false);
        userService.updateUser(user);

        // 删除验证码记录
        userService.deleteVerificationToken(verificationToken);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<String>(200, "User verified successfully", "User verified successfully"));
    }

    @PostMapping("/password/reset/code")
    public ResponseEntity<?> resetPasswordCode(@RequestBody VerificationRequest request) {
        String email = request.getEmail();
        // 检查用户是否存在
        Optional<User> optionalUser = userService.findUsersByEmail(email);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(404, "User not found", "User not found"));
        }
        userService.resetUserPassword(optionalUser.orElseThrow());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<String>(200, "Send Verification Code Successfully", "Send Verification Code Successfully"));
    }

    @PostMapping("/password/reset/verify")
    public ResponseEntity<?> verifyPasswordCode(@RequestBody VerificationRequest request) {
        Optional<User> optionalUser = userService.findUsersByEmail(request.getEmail());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(404, "User not found", "User not found"));
        }
        Optional<VerificationToken> verificationToken = userService.getVerificationTokenByUserId(request.getToken(), optionalUser.orElseThrow().getId());
        if (verificationToken.isPresent() && verificationToken.orElseThrow().isExpired()) {
            // Token无效或已过期
            return new ResponseEntity<>("Token is invalid or expired", HttpStatus.BAD_REQUEST);
        }
        userService.deleteVerificationToken(verificationToken.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<String>(200, "User verified successfully", "User verified successfully"));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null || verificationToken.isExpired()) {
            // Token无效或已过期
            return new ResponseEntity<>("Token is invalid or expired", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User verified successfully!", HttpStatus.OK);
    }

    @PostMapping("/account/clear")
    public ResponseEntity<?> clearAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null || verificationToken.isExpired()) {
            // Token无效或已过期
            return new ResponseEntity<>("Token is invalid or expired", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User verified successfully!", HttpStatus.OK);
    }

    // 新增的微信登录接口
    @PostMapping("/wechat/login")
    public ResponseEntity<ApiResponse<String>> loginWithWeChat(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");
        User user = userService.loginWithWeChat(code);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        "123456"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenProvider.createToken(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<String>(201, "Generate token", token));
    }
}

package com.hab.blog.service;

import com.hab.blog.model.VerificationToken;
import com.hab.blog.repository.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hab.blog.repository.UserRepository;
import com.hab.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(EmailService emailService, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 初始化PasswordEncoder
    }

    public User createUser(User user) {
        if (userRepository.existsByDisplayName(user.getDisplayName())) {
            throw  new IllegalStateException("DisplayName already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw  new IllegalStateException("email already taken");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setDisabled(true);

        User newUser = userRepository.save(user);
        String token = generateNewToken();
        VerificationToken verificationToken = new VerificationToken(token, newUser);
        System.out.printf("verificationToken:saddasd"+verificationToken);
        verificationTokenRepository.save(verificationToken);

        emailService.sendConfirmationEmail(user.getEmail(), token);
        // Set other fields
        return newUser;
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

}

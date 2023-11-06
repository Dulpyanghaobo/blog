package com.hab.blog.service;

import com.hab.blog.repository.UserRepository;
import com.hab.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String displayName, String avatar, String email, String password) {
        User user = new User();
        user.setDisplayName(displayName);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setPassword(password);
        // Set other fields
        return userRepository.save(user);
    }

}

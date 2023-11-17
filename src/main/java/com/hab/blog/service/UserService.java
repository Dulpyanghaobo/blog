package com.hab.blog.service;

import com.hab.blog.dto.UserRegistrationDto;
import com.hab.blog.exception.AlreadyExistsException;
import com.hab.blog.exception.MailException;
import com.hab.blog.model.Role;
import com.hab.blog.model.User;
import com.hab.blog.model.VerificationToken;
import com.hab.blog.repository.UserRepository;
import com.hab.blog.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final EmailService emailService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(EmailService emailService, RoleService roleService, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.emailService = emailService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 初始化PasswordEncoder
    }

    public User createUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUserName(registrationDto.getUserName())) {
            throw new AlreadyExistsException("User", "username", registrationDto.getUserName());

        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw  new MailException("","",registrationDto.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        registrationDto.setPassword(hashedPassword);
        User newUser = new User();
        newUser.setUserName(registrationDto.getUserName());
        newUser.setAvatar(registrationDto.getAvatar());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(registrationDto.getPassword());

        // 处理角色
        Set<Role> roles = new HashSet<>();
        if (registrationDto.getRoles() != null) {
            for (String roleName : registrationDto.getRoles()) {
                // 假设有一个方法来根据角色名称获取角色实体
                Role role = roleService.findByName(roleName);
                roles.add(role);
            }
        } else {
            // 如果没有指定角色，可以分配一个默认角色
            Role defaultRole = roleService.findByName("ROLE_ADMIN");
            roles.add(defaultRole);
        }
        newUser.setRoles(roles);
        newUser.setDisabled(true);

        User user = userRepository.save(newUser);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

}

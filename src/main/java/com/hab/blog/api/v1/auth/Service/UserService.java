package com.hab.blog.api.v1.auth.Service;

import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.dto.UserRegistrationDto;
import com.hab.blog.api.v1.model.VerificationToken;
import com.hab.blog.api.v1.auth.VerificationTokenRepository;
import com.hab.blog.api.v1.response.exception.AlreadyExistsException;
import com.hab.blog.api.v1.response.exception.MailException;
import com.hab.blog.api.v1.service.EmailService;
import com.hab.blog.api.v1.service.RoleService;
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
import java.util.Date;
import java.util.Optional;
import java.util.Random;
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

    public User createUser(UserRegistrationDto registrationDto) throws Exception {
        if (userRepository.existsByUserName(registrationDto.getUserName())) {
            throw new AlreadyExistsException("User", "username", registrationDto.getUserName());

        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw  new MailException("","",registrationDto.getEmail());
        }

        String token = generateNewToken();
        User newUser = new User();
        newUser.setUserName(registrationDto.getUserName());
        newUser.setAvatar(registrationDto.getAvatar());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setRegisteredAt(new Date().toInstant());
        VerificationToken verificationToken = new VerificationToken(token, newUser);
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        registrationDto.setPassword(hashedPassword);
        emailService.sendConfirmationEmail(newUser.getUserName(),newUser.getEmail(), token);
        newUser.setPassword(registrationDto.getPassword());
        newUser.setDisabled(true);

        User user = userRepository.save(newUser);
        verificationTokenRepository.save(verificationToken);
        return user;
    }



    public Optional<User> findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser;
    }

    public Optional<User> findUsersByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser;
    }

    public Optional<VerificationToken> findByUserIdAndToken(String token, Long userId) {
        return verificationTokenRepository.findByUserIdAndToken(userId, token);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public String generateNewToken() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(900000) + 100000;  // 生成100000到999999之间的随机数
        return String.valueOf(randomNumber);
    }
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void deleteVerificationToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
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

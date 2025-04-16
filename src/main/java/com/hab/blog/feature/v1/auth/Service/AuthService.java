package com.hab.blog.feature.v1.auth.Service;

import com.hab.blog.common.config.WeChatConfig;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.auth.Dto.VerificationToken;
import com.hab.blog.feature.v1.auth.Dto.WeChatLoginResponse;
import com.hab.blog.feature.v1.entities.User.Repository.UserRepository;
import com.hab.blog.feature.v1.entities.repository.VerificationTokenRepository;
import com.hab.blog.feature.v1.auth.Dto.UserRegistrationDto;
import com.hab.blog.feature.v1.response.exception.AlreadyExistsException;
import com.hab.blog.feature.v1.response.exception.MailException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private RestTemplate restTemplate;

    public User createUser(UserRegistrationDto registrationDto) throws Exception {
        if (userRepository.existsByUserName(registrationDto.getUserName())) {
            throw new AlreadyExistsException("User", "username", registrationDto.getUserName());
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new MailException("", "", registrationDto.getEmail());
        }

        String token = generateNewToken();
        User newUser = new User();
        newUser.setUserName(registrationDto.getUserName());
        newUser.setAvatar(registrationDto.getAvatar());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setRegisteredAt(Instant.now());
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        registrationDto.setPassword(hashedPassword);
        emailService.sendActivationEmail(newUser.getEmail(), newUser.getUserName(), token);
        newUser.setPassword(registrationDto.getPassword());
        newUser.setName(registrationDto.getUserName());
        newUser.setDisabled(true);
        User user = userRepository.save(newUser);
        VerificationToken verificationToken = new VerificationToken(token, VerificationToken.TokenType.VERIFY_EMAIL, user.getEmail(), user.getId());
        verificationTokenRepository.save(verificationToken);
        return user;
    }

    public User loginWithWeChat(String code) {
        // 调用微信接口获取 openid 和 session_key
        WeChatLoginResponse response = getWeChatSession(code);

        if (response.getErrcode() != null) {
            throw new RuntimeException("微信登录失败: " + response.getErrmsg());
        }

        Optional<User> optionalUser = userRepository.findByOpenid(response.getOpenid());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // 用户不存在，创建新用户
            user = new User();
            user.setOpenid(response.getOpenid());
            user.setUserName("WeChatUser" + System.currentTimeMillis());
            user.setName(String.valueOf(new Random()));

            user.setDisabled(false);
            user.setRegisteredAt(Instant.now());
            // 为微信用户生成随机不可用密码
            String randomPassword = RandomStringUtils.randomAlphanumeric(20);
            user.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(user);
        }
        return user;
    }

    // 调用微信API获取session_key和openid
    private WeChatLoginResponse getWeChatSession(String code) {
        String url = String.format(
                "%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                weChatConfig.getApiJscode2session(), weChatConfig.getAppid(), weChatConfig.getSecret(), code
        );

        return restTemplate.getForObject(url, WeChatLoginResponse.class);
    }

    public void resetUserPassword(User user) {
        String token = generateNewToken();
        VerificationToken verificationToken = new VerificationToken(token, VerificationToken.TokenType.RESET_PASSWORD, user.getEmail());
        emailService.sendReSetPassword(user.getEmail(), user.getUserName(), token);
        verificationTokenRepository.save(verificationToken);
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

    public Optional<VerificationToken> getVerificationTokenByUserId(String token, long userId) {
        return verificationTokenRepository.findByUserIdAndToken(userId, token);
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

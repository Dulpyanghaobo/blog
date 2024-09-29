package com.hab.blog.api.v1.auth.Service;

import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.Entity.WeChatLoginResponse;
import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.response.ApiResponse;
import com.hab.blog.api.v1.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeChatAuthService {

    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ApiResponse<String> loginWithWeChat(String code) {
        WeChatLoginResponse response = getWeChatSession(code);

        if (response.getErrcode() != null) {
            throw new RuntimeException("微信登录失败: " + response.getErrmsg());
        }

        Optional<User> optionalUser = userRepository.findByOpenid(response.getOpenid());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setOpenid(response.getOpenid());
            user.setUserName("WeChatUser" + System.currentTimeMillis());
            user.setDisabled(false);
            user.setRegisteredAt(Instant.now());
            userRepository.save(user);
        }

        String token = jwtTokenProvider.createToken(new UsernamePasswordAuthenticationToken(user.getUserName(), null, getAuthorities(user)));

        return new ApiResponse<>(200, "Login successful", token);
    }

    private WeChatLoginResponse getWeChatSession(String code) {
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, appSecret, code);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, WeChatLoginResponse.class);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }
}


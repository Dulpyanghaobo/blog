import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.Entity.WeChatLoginResponse;
import com.hab.blog.api.v1.auth.Service.WeChatAuthService;
import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.response.ApiResponse;
import com.hab.blog.api.v1.utility.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeChatAuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private WeChatAuthService authService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(authService, "appId", "id");
        ReflectionTestUtils.setField(authService, "appSecret", "secret");
    }

    @Test
    void loginWithWeChat_createsUserWhenNotFound() {
        WeChatLoginResponse response = new WeChatLoginResponse();
        response.setOpenid("openid");
        try (MockedConstruction<RestTemplate> mocked = mockConstruction(RestTemplate.class,
            (mock, ctx) -> when(mock.getForObject(anyString(), eq(WeChatLoginResponse.class))).thenReturn(response))) {
            when(userRepository.findByOpenid("openid")).thenReturn(Optional.empty());
            when(jwtTokenProvider.createToken(any(UsernamePasswordAuthenticationToken.class))).thenReturn("token");

            ApiResponse<String> result = authService.loginWithWeChat("code");

            assertThat(result.getData()).isEqualTo("token");
            verify(userRepository).save(any(User.class));
        }
    }

    @Test
    void loginWithWeChat_throwsWhenErrorReturned() {
        WeChatLoginResponse response = new WeChatLoginResponse();
        response.setErrcode(1);
        response.setErrmsg("error");
        try (MockedConstruction<RestTemplate> mocked = mockConstruction(RestTemplate.class,
            (mock, ctx) -> when(mock.getForObject(anyString(), eq(WeChatLoginResponse.class))).thenReturn(response))) {
            assertThatThrownBy(() -> authService.loginWithWeChat("code"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("微信登录失败");
        }
    }
}

import com.hab.blog.api.v1.Objective.dto.JwtRequestDto;
import com.hab.blog.api.v1.Objective.dto.UserRegistrationDto;
import com.hab.blog.api.v1.auth.AuthController;
import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.Entity.VerificationRequest;
import com.hab.blog.api.v1.auth.Entity.VerificationToken;
import com.hab.blog.api.v1.auth.Service.UserService;
import com.hab.blog.api.v1.response.ApiResponse;
import com.hab.blog.api.v1.response.exception.AlreadyExistsException;
import com.hab.blog.api.v1.utility.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    void setup() {
        // fields are injected via Mockito @InjectMocks, but ensure
        ReflectionTestUtils.setField(controller, "authenticationManager", authenticationManager);
        ReflectionTestUtils.setField(controller, "jwtTokenProvider", jwtTokenProvider);
        ReflectionTestUtils.setField(controller, "userService", userService);
    }

    @Test
    void createAuthenticationToken_returnsToken() throws Exception {
        JwtRequestDto dto = new JwtRequestDto();
        dto.setEmail("test@example.com");
        dto.setPassword("pass");
        User user = new User();
        user.setUserName("tester");
        when(userService.findUsersByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtTokenProvider.createToken(auth)).thenReturn("jwt");

        ResponseEntity<?> response = controller.createAuthenticationToken(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ApiResponse<?> body = (ApiResponse<?>) response.getBody();
        assertThat(body.getData()).isEqualTo("jwt");
    }

    @Test
    void registerUser_returnsConflictWhenExists() throws Exception {
        UserRegistrationDto dto = new UserRegistrationDto();
        doThrow(new AlreadyExistsException("User", "username", "u")).when(userService).createUser(dto);

        ResponseEntity<ApiResponse<String>> response = controller.registerUser(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void confirmRegistration_success() {
        VerificationRequest request = new VerificationRequest();
        request.setToken("tok");
        request.setUserId(1L);
        User user = new User();
        VerificationToken token = new VerificationToken("tok", VerificationToken.TokenType.VERIFY_EMAIL, "e", 1L);

        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(userService.findByUserIdAndToken("tok", 1L)).thenReturn(Optional.of(token));
        ResponseEntity<?> response = controller.confirmRegistration(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService).updateUser(user);
        verify(userService).deleteVerificationToken(token);
    }
}

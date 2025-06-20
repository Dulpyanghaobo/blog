import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.auth.VerificationTokenRepository;
import com.hab.blog.api.v1.auth.Service.UserService;
import com.hab.blog.api.v1.Objective.dto.UserRegistrationDto;
import com.hab.blog.api.v1.response.exception.AlreadyExistsException;
import com.hab.blog.api.v1.response.exception.MailException;
import com.hab.blog.api.v1.service.EmailService;
import com.hab.blog.api.v1.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private EmailService emailService;
    @Mock
    private RoleService roleService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDto dto;

    @BeforeEach
    void setup() {
        dto = new UserRegistrationDto();
        dto.setUserName("tester");
        dto.setAvatar("avatar");
        dto.setEmail("test@example.com");
        dto.setPassword("password");
    }

    @Test
    void createUser_savesUserAndToken() throws Exception {
        when(userRepository.existsByUserName(dto.getUserName())).thenReturn(false);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        User saved = new User();
        saved.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.createUser(dto);

        assertThat(result).isEqualTo(saved);
        verify(userRepository).save(any(User.class));
        verify(verificationTokenRepository).save(any());
        verify(emailService).sendActivationEmail(eq(dto.getEmail()), eq(dto.getUserName()), anyString());
    }

    @Test
    void createUser_whenUsernameExists_throwException() {
        when(userRepository.existsByUserName(dto.getUserName())).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(AlreadyExistsException.class);
    }

    @Test
    void createUser_whenEmailExists_throwException() {
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);
        when(userRepository.existsByUserName(dto.getUserName())).thenReturn(false);
        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(MailException.class);
    }

    @Test
    void generateNewToken_returnsSixDigitString() {
        String token = userService.generateNewToken();
        assertThat(token).hasSize(6).matches("\\d{6}");
    }
}

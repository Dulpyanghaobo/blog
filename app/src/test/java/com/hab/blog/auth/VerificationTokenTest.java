import com.hab.blog.api.v1.auth.Entity.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationTokenTest {

    @Test
    void isExpired_returnsTrueWhenExpiryDatePast() {
        VerificationToken token = new VerificationToken("t", VerificationToken.TokenType.VERIFY_EMAIL, "e", 1L);
        ReflectionTestUtils.setField(token, "expiryDate", Instant.now().minusSeconds(60));
        assertThat(token.isExpired()).isTrue();
    }

    @Test
    void constructor_setsFutureExpiryDate() {
        VerificationToken token = new VerificationToken("t", VerificationToken.TokenType.RESET_PASSWORD, "e", 1L);
        assertThat(token.getExpiryDate()).isAfter(Instant.now());
        assertThat(token.getToken()).isEqualTo("t");
        assertThat(token.getTokenType()).isEqualTo(VerificationToken.TokenType.RESET_PASSWORD);
    }
}

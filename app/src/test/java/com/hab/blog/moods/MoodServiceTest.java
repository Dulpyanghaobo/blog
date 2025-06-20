import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.moods.Entity.Mood;
import com.hab.blog.api.v1.moods.Repository.MoodRepository;
import com.hab.blog.api.v1.moods.Service.MoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoodServiceTest {

    @Mock
    private MoodRepository moodRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MoodService moodService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
    }

    @Test
    void createMood_shouldSaveAndReturnMood() {
        LocalDate date = LocalDate.now();
        int level = 3;
        Mood mood = new Mood();
        mood.setUser(user);
        mood.setDate(date);
        mood.setMoodLevel(level);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(moodRepository.save(any(Mood.class))).thenReturn(mood);

        Mood result = moodService.createMood(user.getId(), date, level);

        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getDate()).isEqualTo(date);
        assertThat(result.getMoodLevel()).isEqualTo(level);
        verify(moodRepository).save(any(Mood.class));
    }

    @Test
    void updateMood_shouldUpdateExistingRecord() {
        LocalDate date = LocalDate.now();
        Mood existing = new Mood();
        existing.setUser(user);
        existing.setDate(date);
        existing.setMoodLevel(1);
        when(moodRepository.findByUserIdAndDate(user.getId(), date)).thenReturn(Optional.of(existing));
        when(moodRepository.save(existing)).thenReturn(existing);

        Mood result = moodService.updateMood(user.getId(), date, 5);

        assertThat(result.getMoodLevel()).isEqualTo(5);
        verify(moodRepository).save(existing);
    }

    @Test
    void updateMood_shouldThrowIfNotFound() {
        when(moodRepository.findByUserIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> moodService.updateMood(1L, LocalDate.now(), 2));
    }
}

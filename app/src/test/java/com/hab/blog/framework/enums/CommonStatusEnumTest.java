import com.hab.blog.api.v1.framework.common.enums.CommonStatusEnum;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonStatusEnumTest {

    @Test
    void array_containsAllStatuses() {
        int[] array = CommonStatusEnum.ENABLE.array();
        assertThat(array).containsExactlyInAnyOrder(0, 1);
    }

    @Test
    void isEnable_and_isDisable_workCorrectly() {
        assertThat(CommonStatusEnum.isEnable(0)).isTrue();
        assertThat(CommonStatusEnum.isDisable(0)).isFalse();
        assertThat(CommonStatusEnum.isDisable(1)).isTrue();
    }
}

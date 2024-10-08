package com.hab.blog.feature.v1.entities.astrology.Repository;

import com.hab.blog.feature.v1.entities.astrology.UserDailyFortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserDailyFortuneRepository extends JpaRepository<UserDailyFortune, Long> {
    // 查询用户特定日期的运势
    Optional<UserDailyFortune> findFirstByUserIdAndFortuneDate(Long userId, LocalDate fortuneDate);
}
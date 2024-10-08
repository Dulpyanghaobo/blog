package com.hab.blog.feature.v1.entities.astrology.Repository;

import com.hab.blog.feature.v1.entities.astrology.UserAstrology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserAstrologyRepository extends JpaRepository<UserAstrology, Long> {
    // 基于 user_id 和当前日期查询用户当天的星象事件
    @Query("SELECT ua FROM UserAstrology ua " +
            "JOIN ua.astrologyEvent ae " +
            "WHERE ua.user.id = :userId AND ae.eventDate = :currentDate")
    Optional<UserAstrology> findByUserIdAndCurrentDate(@Param("userId") Long userId,
                                                       @Param("currentDate") LocalDate currentDate);
}
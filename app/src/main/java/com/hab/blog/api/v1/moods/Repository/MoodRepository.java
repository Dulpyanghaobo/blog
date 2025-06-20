package com.hab.blog.api.v1.moods.Repository;

import com.hab.blog.api.v1.moods.Entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {
    List<Mood> findByUserId(Long userId);
    Optional<Mood> findByUserIdAndDate(Long userId, LocalDate date);
}

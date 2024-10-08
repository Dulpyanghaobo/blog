package com.hab.blog.feature.v1.entities.tarot.repository;

import com.hab.blog.feature.v1.entities.tarot.DailyHoroscope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyHoroscopeRepository extends JpaRepository<DailyHoroscope, Long> {

}
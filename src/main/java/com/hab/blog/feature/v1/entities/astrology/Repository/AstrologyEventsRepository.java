package com.hab.blog.feature.v1.entities.astrology.Repository;

import com.hab.blog.feature.v1.entities.astrology.AstrologyEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstrologyEventsRepository extends JpaRepository<AstrologyEvents, Long> {

}
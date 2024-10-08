package com.hab.blog.feature.v1.entities.tarot.repository;

import com.hab.blog.feature.v1.entities.tarot.TarotSpread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarotSpreadRepository extends JpaRepository<TarotSpread, Long> {
}

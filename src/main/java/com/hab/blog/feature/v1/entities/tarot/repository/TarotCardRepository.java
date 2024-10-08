package com.hab.blog.feature.v1.entities.tarot.repository;

import com.hab.blog.feature.v1.entities.tarot.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {

}
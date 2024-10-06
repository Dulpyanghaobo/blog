package com.hab.blog.feature.v1.entities.carot.repository;

import com.hab.blog.feature.v1.entities.carot.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {

}
package com.hab.blog.feature.v1.entities.tarot.repository;

import com.hab.blog.feature.v1.entities.tarot.TarotCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarotCategoryRepository extends JpaRepository<TarotCategory, Long> {
}

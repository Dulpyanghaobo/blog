package com.hab.blog.feature.v1.repository;

import com.hab.blog.feature.v1.Objective.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    List<Objective> findByUserId(Long userId);
}

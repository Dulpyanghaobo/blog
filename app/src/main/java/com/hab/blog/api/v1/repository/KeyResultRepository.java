package com.hab.blog.api.v1.repository;

import com.hab.blog.api.v1.Objective.model.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyResultRepository extends JpaRepository<KeyResult, Long> {
    List<KeyResult> findByObjectiveId(Long objectiveId);
    List<KeyResult> findByUserId(Long userId);
}

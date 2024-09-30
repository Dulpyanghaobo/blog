package com.hab.blog.feature.v1.entities.repository;

import com.hab.blog.feature.v1.entities.Task.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyResultRepository extends JpaRepository<KeyResult, Long> {
    List<KeyResult> findByObjectiveId(Long objectiveId);
    List<KeyResult> findByUserId(Long userId);
}

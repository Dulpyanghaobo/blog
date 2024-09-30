package com.hab.blog.feature.v1.entities.repository;

import com.hab.blog.feature.v1.entities.Task.KeyAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyActionRepository extends JpaRepository<KeyAction, Long> {
    List<KeyAction> findByObjectiveId(Long objectiveId);
    List<KeyAction> findByUserId(Long userId);
}


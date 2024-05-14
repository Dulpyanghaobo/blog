package com.hab.blog.api.v1.repository;

import com.hab.blog.api.v1.model.KeyAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyActionRepository extends JpaRepository<KeyAction, Long> {
    List<KeyAction> findByObjectiveId(Long objectiveId);
    List<KeyAction> findByUserId(Long userId);
}


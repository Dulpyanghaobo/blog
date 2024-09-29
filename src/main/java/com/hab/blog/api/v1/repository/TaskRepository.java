package com.hab.blog.api.v1.repository;

import com.hab.blog.api.v1.Objective.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByObjectiveId(Long objectiveId);
    List<Task> findByUserId(Long userId);
}


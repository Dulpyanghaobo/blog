package com.hab.blog.feature.v1.service;

import com.hab.blog.feature.v1.dto.TaskDto;
import com.hab.blog.feature.v1.model.Task;
import com.hab.blog.feature.v1.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Method to create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Method to update an existing task
    public Task updateTask(Long taskId, TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            // Update properties of task from taskDto
            // For example:
            // task.setTitle(taskDto.getTitle());
            // ... update other fields ...

            return taskRepository.save(task);
        } else {
            // Handle the case where the task is not found
            // You might throw an exception or return null
            throw new IllegalStateException("failed to update Task");
        }
    }

    // Method to get a task by ID
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    // Add more methods as needed for your application logic
}

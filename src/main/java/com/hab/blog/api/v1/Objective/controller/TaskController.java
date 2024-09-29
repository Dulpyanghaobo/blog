package com.hab.blog.api.v1.Objective.controller;

import com.hab.blog.api.v1.service.TaskService;
import com.hab.blog.api.v1.Objective.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task") // 控制器的基础URL
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            // 假设用户的信息是通过安全上下文或者请求参数传递进来的
            // 在这里，我们直接将接收到的Post对象保存
            Task createdTask = taskService.createTask(task);
            // 创建成功后，返回新创建的帖子和HTTP状态201 Created
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            // 发生异常时返回HTTP状态500 Internal Server Error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

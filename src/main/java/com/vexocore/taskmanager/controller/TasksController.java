package com.vexocore.taskmanager.controller;

import com.vexocore.taskmanager.dto.TaskRequest;
import com.vexocore.taskmanager.entity.Tasks;
import com.vexocore.taskmanager.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Tasks> addTask(
            @RequestBody TaskRequest request,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(taskService.addTask(request, token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tasks> updateTask(
            @PathVariable Integer id,
            @RequestBody Tasks updatedTask) {

        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteTask(@PathVariable Integer id) {

        return taskService.softDeleteTask(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Tasks> getActiveTasksByUser(@PathVariable Integer userId) {
        return taskService.getActiveTasksByUser(userId);
    }
}

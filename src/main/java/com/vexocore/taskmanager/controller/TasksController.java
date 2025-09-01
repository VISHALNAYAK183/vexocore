package com.vexocore.taskmanager.controller;

import com.vexocore.taskmanager.dto.TaskRequest;
import com.vexocore.taskmanager.entity.Tasks;
import com.vexocore.taskmanager.repository.TaskRepository;
import com.vexocore.taskmanager.security.JwtUtil;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Tasks> addTask(@RequestBody TaskRequest request,
                                         @RequestHeader("Authorization") String token) {

        // Remove "Bearer " prefix
        String jwt = token.replace("Bearer ", "");

        // Extract claims
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = Integer.valueOf((String) claims.get("id")); // user id from token

        // Create and save new task
        Tasks task = new Tasks(userId, request.getTitle(), request.getDescription());
        Tasks savedTask = taskRepository.save(task);

        return ResponseEntity.ok(savedTask);
    }
}

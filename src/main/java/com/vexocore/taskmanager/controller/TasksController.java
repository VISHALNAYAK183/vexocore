package com.vexocore.taskmanager.controller;

import com.vexocore.taskmanager.dto.TaskRequest;
import com.vexocore.taskmanager.entity.Tasks;
import com.vexocore.taskmanager.repository.TaskRepository;
import com.vexocore.taskmanager.security.JwtUtil;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.List;

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

    
        String jwt = token.replace("Bearer ", "");

     
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = Integer.valueOf((String) claims.get("id")); // user id from token

       
        Tasks task = new Tasks(userId, request.getTitle(), request.getDescription());
        Tasks savedTask = taskRepository.save(task);

        return ResponseEntity.ok(savedTask);
    }
      @PutMapping("/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable Integer id, @RequestBody Tasks updatedTask) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setUpdatedAt(LocalDateTime.now()); // update timestamp
                    Tasks savedTask = taskRepository.save(existingTask);
                    return ResponseEntity.ok(savedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
public ResponseEntity<String> softDeleteTask(@PathVariable Integer id) {
    return taskRepository.findById(id)
            .map(task -> {
                task.setActive("N"); // mark as deleted
                task.setUpdatedAt(LocalDateTime.now());
                taskRepository.save(task);
                return ResponseEntity.ok("Task soft-deleted successfully.");
            })
            .orElse(ResponseEntity.notFound().build());
}
@GetMapping("/user/{userId}")
public List<Tasks> getActiveTasksByUser(@PathVariable Integer userId) {
    return taskRepository.findByUserIdAndActive(userId, "Y");
}


}

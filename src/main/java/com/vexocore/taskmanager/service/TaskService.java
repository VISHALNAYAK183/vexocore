package com.vexocore.taskmanager.service;

import com.vexocore.taskmanager.dto.TaskRequest;
import com.vexocore.taskmanager.entity.Tasks;
import com.vexocore.taskmanager.repository.TaskRepository;
import com.vexocore.taskmanager.security.JwtUtil;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Tasks addTask(TaskRequest request, String token) {

        String jwt = token.replace("Bearer ", "");
        Claims claims = jwtUtil.extractAllClaims(jwt);

        Integer userId = Integer.valueOf((String) claims.get("id"));

        Tasks task = new Tasks(userId, request.getTitle(), request.getDescription());

        return taskRepository.save(task);
    }

    public Optional<Tasks> updateTask(Integer id, Tasks updatedTask) {

        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setUpdatedAt(LocalDateTime.now());
                    return taskRepository.save(existingTask);
                });
    }

  
    public Optional<String> softDeleteTask(Integer id) {

        return taskRepository.findById(id)
                .map(task -> {
                    task.setActive("N");
                    task.setUpdatedAt(LocalDateTime.now());
                    taskRepository.save(task);
                    return "Task soft-deleted successfully.";
                });
    }

   
    public List<Tasks> getActiveTasksByUser(Integer userId) {
        return taskRepository.findByUserIdAndActive(userId, "Y");
    }
}

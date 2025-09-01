package com.vexocore.taskmanager.repository;

import com.vexocore.taskmanager.entity.Tasks;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    List<Tasks> findByUserIdAndActive(Integer userId, String active);
}

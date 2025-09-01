package com.vexocore.taskmanager.repository;

import com.vexocore.taskmanager.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Integer> {
}

package com.prueba.tareas.repository;

import com.prueba.tareas.model.Task;
import com.prueba.tareas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    Optional<Task> findById(Long id);
    Task findByIdAndUserId(Long id, Long userId);
}

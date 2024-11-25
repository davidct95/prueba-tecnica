package com.prueba.tareas.service;

import com.prueba.tareas.dto.TaskDto;
import com.prueba.tareas.model.Task;
import com.prueba.tareas.model.User;
import com.prueba.tareas.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TaskRepository taskRepository;

    public List<TaskDto> getTasksForAuthenticatedUser(){
        User authenticatedUser = this.authenticationService.getAuthenticatedUser();
        List<Task> tasks = authenticatedUser.getTasksList();
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getExpirationDate(), task.getIsComplete()))
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {

        User authenticatedUser = this.authenticationService.getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUserId(id, authenticatedUser.getId());

        TaskDto taskDto = new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getExpirationDate(),
                task.getIsComplete());

        return taskDto;
    }

    public void createTask(TaskDto taskDto) {
        User authenticatedUser = this.authenticationService.getAuthenticatedUser();

        Task task = Task.builder()
                .title(taskDto.title())
                .description(taskDto.description())
                .expirationDate(taskDto.expirationDate())
                .isComplete(taskDto.isComplete())
                .user(authenticatedUser)
                .build();

        this.taskRepository.save(task);
    }

    public void updateTask(Long id, TaskDto taskDto) {

        User authenticatedUser = this.authenticationService.getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUserId(id, authenticatedUser.getId());

        if(task != null) {
            Task updateTask = Task.builder()
                    .id(task.getId())
                    .title(taskDto.title())
                    .description(taskDto.description())
                    .expirationDate(taskDto.expirationDate())
                    .isComplete(taskDto.isComplete())
                    .user(authenticatedUser)
                    .build();

            this.taskRepository.save(updateTask);
        } else {
            throw new AccessDeniedException("No tienes permiso para actualizar esta tarea");
        }

    }

    public void deleteTask(Long id) {
        User authenticatedUser = this.authenticationService.getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUserId(id, authenticatedUser.getId());

        if(task != null) {
            this.taskRepository.delete(task);
        }
    }
}

package com.prueba.tareas.controller;

import com.prueba.tareas.dto.TaskDto;
import com.prueba.tareas.model.Task;
import com.prueba.tareas.repository.TaskRepository;
import com.prueba.tareas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public List<TaskDto> listTask() {
        return taskService.getTasksForAuthenticatedUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);

    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto){
        this.taskService.updateTask(id, taskDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

}

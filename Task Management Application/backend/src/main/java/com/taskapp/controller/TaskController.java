package com.taskapp.controller;

import com.taskapp.model.Task;
import com.taskapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(@AuthenticationPrincipal UserDetails user) {
        return taskService.getTasks(user.getUsername());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails user) {
        return taskService.createTask(task, user.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task,
                                        @AuthenticationPrincipal UserDetails user) {
        try {
            return ResponseEntity.ok(taskService.updateTask(id, task, user.getUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/account")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails user) {
        taskService.deleteAccount(user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails user) {
        try {
            taskService.deleteTask(id, user.getUsername());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

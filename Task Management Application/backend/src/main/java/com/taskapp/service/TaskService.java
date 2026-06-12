package com.taskapp.service;

import com.taskapp.model.Task;
import com.taskapp.model.User;
import com.taskapp.repository.TaskRepository;
import com.taskapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<Task> getTasks(String username) {
        return taskRepository.findByUserId(getUser(username).getId());
    }

    public Task createTask(Task task, String username) {
        task.setUser(getUser(username));
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updated, String username) {
        Task task = taskRepository.findByIdAndUserId(id, getUser(username).getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setStatus(updated.getStatus());
        task.setPriority(updated.getPriority());
        task.setDueDate(updated.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteAccount(String username) {
        User user = getUser(username);
        taskRepository.deleteAll(taskRepository.findByUserId(user.getId()));
        userRepository.delete(user);
    }

    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findByIdAndUserId(id, getUser(username).getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }
}

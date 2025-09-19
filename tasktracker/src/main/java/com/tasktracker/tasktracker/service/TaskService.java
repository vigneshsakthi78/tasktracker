package com.tasktracker.tasktracker.service;

import com.tasktracker.tasktracker.model.Task;
import com.tasktracker.tasktracker.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> searchByTitle(String q) {
        return taskRepository.findByTitleContainingIgnoreCase(q == null ? "" : q);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}

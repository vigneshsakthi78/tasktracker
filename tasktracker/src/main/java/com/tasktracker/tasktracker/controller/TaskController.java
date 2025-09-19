package com.tasktracker.tasktracker.controller;

import com.tasktracker.tasktracker.model.Task;
import com.tasktracker.tasktracker.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task_form";
    }

    @PostMapping("/tasks")
    public String saveTask(@Valid @ModelAttribute Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "task_form";
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping({ "/tasks", "/" })
    public String listTasks(@RequestParam(required = false) String q, Model model) {
        List<Task> tasks;
        if (q != null && !q.isBlank()) {
            tasks = taskService.searchByTitle(q);
        } else {
            tasks = taskService.findAll();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("q", q);
        return "task_list";
    }

    @GetMapping("/tasks/{id}")
    public String taskDetails(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        Task found = taskService.findById(id);
        if (found == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", found);
        return "task_details";
    }

    @GetMapping("/tasks/{id}/edit")
    public String editTask(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        Task found = taskService.findById(id);
        if (found == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", found);
        return "task_form";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleDone(@org.springframework.web.bind.annotation.PathVariable Long id) {
        Task t = taskService.findById(id);
        if (t != null) {
            t.setDone(!t.isDone());
            taskService.save(t);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@org.springframework.web.bind.annotation.PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}

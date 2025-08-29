package com.curt.projectmanagemment.controller;

import com.curt.projectmanagemment.entities.Task;
import com.curt.projectmanagemment.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Create a new task inside a project
    @PostMapping("/{projectId}")
    public Task addTask(@PathVariable Long projectId, @RequestBody Task task) {
        return taskService.addTaskToProject(projectId, task);
    }

    // Get all tasks for a specific project
    @GetMapping("/{projectId}")
    public List<Task> getTasks(@PathVariable Long projectId) {
        return taskService.getTasksByProject(projectId);
    }

    // Update a specific task
    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        return taskService.updateTask(taskId, task);
    }

    // Delete a specific task
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

}

package com.editorflow.controller;

import com.editorflow.entity.Task;
import com.editorflow.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> addTask(
            @PathVariable Long projectId,
            @RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        return ResponseEntity.ok(taskService.addTask(projectId, title));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getProjectTasks(projectId));
    }

    @PutMapping("/{taskId}/toggle")
    public ResponseEntity<Task> toggleTaskCompleted(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.toggleTaskCompleted(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}

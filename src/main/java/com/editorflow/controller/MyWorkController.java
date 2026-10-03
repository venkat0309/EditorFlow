package com.editorflow.controller;

import com.editorflow.dto.common.ApiResponse;
import com.editorflow.dto.response.ProjectResponse;
import com.editorflow.dto.response.TaskResponse;
import com.editorflow.security.CustomUserDetails;
import com.editorflow.service.ProjectService;
import com.editorflow.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/my-work")
public class MyWorkController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public MyWorkController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/projects")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getMyProjects(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ApiResponse<List<ProjectResponse>> response = new ApiResponse<>(
                true,
                "Assigned projects retrieved successfully",
                projectService.getProjectsAssignedTo(userDetails.getId()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMyTasks(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        ApiResponse<List<TaskResponse>> response = new ApiResponse<>(
                true,
                "Assigned tasks retrieved successfully",
                taskService.getTasksAssignedTo(userDetails.getId()));

        return ResponseEntity.ok(response);
    }
}

package com.editorflow.controller;

import com.editorflow.dto.common.ApiResponse;

//package com.editorflow.backend.controller;

import com.editorflow.dto.request.CreateProjectRequest;
import com.editorflow.dto.request.UpdateProjectRequest;
import com.editorflow.dto.response.ProjectResponse;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.service.ProjectService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.editorflow.security.CustomUserDetails;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

        private final ProjectRepository projectRepository;
        private final ProjectService projectService;

        public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
                this.projectService = projectService;
                this.projectRepository = projectRepository;
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
                        @Valid @RequestBody CreateProjectRequest request) {

                ProjectResponse response = projectService.createProject(request);

                ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>(
                                true,
                                "Project created successfully",
                                response);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(apiResponse);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(
                        @PathVariable Long id) {

                ProjectResponse response = projectService.getProjectById(id);

                ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>(
                                true,
                                "Project retrieved successfully",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        // @GetMapping
        // public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {

        // List<ProjectResponse> response = projectService.getAllProjects();

        // ApiResponse<List<ProjectResponse>> apiResponse = new ApiResponse<>(
        // true,
        // "Projects retrieved successfully",
        // response);

        // return ResponseEntity.ok(apiResponse);
        // }

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getAllProjects(
                        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

                Page<ProjectResponse> projects = projectService.getAllProjects(pageable);

                ApiResponse<Page<ProjectResponse>> response = new ApiResponse<>(
                                true,
                                "Projects retrieved successfully",
                                projects);

                return ResponseEntity.ok(response);
        }

        @GetMapping("/assigned-to-me")
        public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAssignedProjects(Authentication authentication) {

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                List<ProjectResponse> projects = projectService.getProjectsAssignedTo(userDetails.getId());

                ApiResponse<List<ProjectResponse>> response = new ApiResponse<>(
                                true,
                                "Assigned projects retrieved successfully",
                                projects);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable Long id,
                        @Valid @RequestBody UpdateProjectRequest request) {

                ProjectResponse response = projectService.updateProject(id, request);

                ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>(
                                true,
                                "Project Updated successfully",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        @PutMapping("/{id}/assign-editor/{editorId}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<ProjectResponse>> assignEditor(
                        @PathVariable Long id,
                        @PathVariable Long editorId) {

                ProjectResponse response = projectService.assignEditor(id, editorId);

                ApiResponse<ProjectResponse> apiResponse = new ApiResponse<>(
                                true,
                                "Editor assigned successfully",
                                response);

                return ResponseEntity.ok(apiResponse);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<Void>> deleteProject(
                        @PathVariable Long id) {

                System.out.println("Inside delete project method");

                projectService.deleteProject(id);

                ApiResponse<Void> apiResponse = new ApiResponse<>(
                                true,
                                "Project deleted successfully",
                                null);

                return ResponseEntity.ok(apiResponse);
        }

}

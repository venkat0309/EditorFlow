package com.editorflow.serviceImpl;

//package com.editorflow.backend.service.impl;

import com.editorflow.dto.request.CreateProjectRequest;
import com.editorflow.dto.request.UpdateProjectRequest;
import com.editorflow.dto.response.ProjectResponse;
import com.editorflow.entity.Project;
import com.editorflow.entity.ProjectStatus;
import com.editorflow.entity.Role;
import com.editorflow.entity.User;
import com.editorflow.exception.ProjectNotFoundException;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.UserRepository;
import com.editorflow.service.ProjectService;

import java.util.List;

//import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setClientName(request.getClientName());
        project.setClientEmail(request.getClientEmail());
        project.setDueDate(request.getDueDate());
        if (request.getAssignedEditorId() != null) {
            project.setAssignedEditor(getEditor(request.getAssignedEditorId()));
        }
        project.setStatus(ProjectStatus.CREATED);

        Project savedProject = projectRepository.save(project);

        // Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        return mapToResponse(project);
    }

    private ProjectResponse mapToResponse(Project project) {

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setClientName(project.getClientName());
        response.setClientEmail(project.getClientEmail());
        response.setDueDate(project.getDueDate());
        response.setStatus(project.getStatus());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        if (project.getAssignedEditor() != null) {
            response.setAssignedEditorId(project.getAssignedEditor().getId());
            response.setAssignedEditorName(project.getAssignedEditor().getName());
            response.setAssignedEditorEmail(project.getAssignedEditor().getEmail());
        }

        return response;
    }

    @Override
    public List<ProjectResponse> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(Long id, UpdateProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        project.setTitle(request.getTitle());
        project.setClientName(request.getClientName());
        project.setClientEmail(request.getClientEmail());
        project.setDescription(request.getDescription());
        project.setDueDate(request.getDueDate());
        if (request.getAssignedEditorId() != null) {
            project.setAssignedEditor(getEditor(request.getAssignedEditorId()));
        }

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);

    }

    @Override
    public ProjectResponse assignEditor(Long projectId, Long editorId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        project.setAssignedEditor(getEditor(editorId));
        if (project.getStatus() == ProjectStatus.CREATED) {
            project.setStatus(ProjectStatus.IN_PROGRESS);
        }

        return mapToResponse(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponse> getProjectsAssignedTo(Long editorId) {
        return projectRepository.findByAssignedEditorIdOrderByCreatedAtDesc(editorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        projectRepository.delete(project);
    }

    @Override
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {

        return projectRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    private User getEditor(Long editorId) {
        User user = userRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found with id: " + editorId));

        if (user.getRole() != Role.EDITOR) {
            throw new RuntimeException("User with id " + editorId + " is not an editor");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("Editor with id " + editorId + " is disabled");
        }

        return user;
    }
}

package com.editorflow.serviceImpl;

//package com.editorflow.backend.service.impl;

import com.editorflow.dto.request.CreateProjectRequest;
import com.editorflow.dto.request.UpdateProjectRequest;
import com.editorflow.dto.response.ProjectResponse;
import com.editorflow.entity.Project;
import com.editorflow.entity.ProjectStatus;
import com.editorflow.exception.ProjectNotFoundException;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.service.ProjectService;

import java.util.List;
import java.util.Optional;

//import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {

        Project project = new Project();

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setClientName(request.getClientName());
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
        response.setStatus(project.getStatus());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());

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
        project.setDescription(request.getDescription());

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);

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
}
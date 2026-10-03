package com.editorflow.service;

import com.editorflow.dto.request.CreateProjectRequest;
import com.editorflow.dto.request.UpdateProjectRequest;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.editorflow.dto.response.ProjectResponse;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);

    ProjectResponse getProjectById(Long id);

    List<ProjectResponse> getAllProjects();

    ProjectResponse updateProject(Long id, UpdateProjectRequest request);

    ProjectResponse assignEditor(Long projectId, Long editorId);

    List<ProjectResponse> getProjectsAssignedTo(Long editorId);

    void deleteProject(Long id);

    Page<ProjectResponse> getAllProjects(Pageable pageable);

}

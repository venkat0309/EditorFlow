package com.editorflow.serviceImpl;

import com.editorflow.entity.Project;
import com.editorflow.entity.ProjectStatus;
import com.editorflow.repository.*;
import com.editorflow.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProjectRepository projectRepository;
    private final VideoVersionRepository videoVersionRepository;
    private final CommentRepository commentRepository;

    public DashboardServiceImpl(ProjectRepository projectRepository,
                                 VideoVersionRepository videoVersionRepository,
                                 CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.videoVersionRepository = videoVersionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        List<Project> allProjects = projectRepository.findAll();

        long totalProjects = allProjects.size();
        long activeProjects = allProjects.stream().filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS || p.getStatus() == ProjectStatus.IN_REVIEW).count();
        long approvedProjects = allProjects.stream().filter(p -> p.getStatus() == ProjectStatus.APPROVED || p.getStatus() == ProjectStatus.COMPLETED).count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProjects", totalProjects);
        stats.put("activeProjects", activeProjects);
        stats.put("approvedProjects", approvedProjects);
        stats.put("totalVersions", videoVersionRepository.count());
        stats.put("totalComments", commentRepository.count());

        return stats;
    }
}

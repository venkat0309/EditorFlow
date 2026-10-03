package com.editorflow.serviceImpl;

import com.editorflow.entity.Project;
import com.editorflow.entity.ProjectStatus;
import com.editorflow.entity.VideoVersion;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.VideoVersionRepository;
import com.editorflow.service.FileStorageService;
import com.editorflow.service.VideoVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VideoVersionServiceImpl implements VideoVersionService {

    private final VideoVersionRepository videoVersionRepository;
    private final ProjectRepository projectRepository;
    private final FileStorageService fileStorageService;

    public VideoVersionServiceImpl(VideoVersionRepository videoVersionRepository,
                                  ProjectRepository projectRepository,
                                  FileStorageService fileStorageService) {
        this.videoVersionRepository = videoVersionRepository;
        this.projectRepository = projectRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public VideoVersion uploadVersion(Long projectId, MultipartFile file, String notes, Double durationInSeconds) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        String storedFileName = fileStorageService.storeFile(file);

        List<VideoVersion> existingVersions = videoVersionRepository.findByProjectIdOrderByVersionNumberDesc(projectId);
        int nextVersionNumber = existingVersions.isEmpty() ? 1 : existingVersions.get(0).getVersionNumber() + 1;

        // Deactivate previous versions
        for (VideoVersion v : existingVersions) {
            v.setActive(false);
            videoVersionRepository.save(v);
        }

        VideoVersion version = new VideoVersion();
        version.setProject(project);
        version.setVersionNumber(nextVersionNumber);
        version.setFileKey(storedFileName);
        version.setFileName(file.getOriginalFilename());
        version.setFileSize(file.getSize());
        version.setDurationInSeconds(durationInSeconds != null ? durationInSeconds : 0.0);
        version.setNotes(notes);
        version.setActive(true);

        VideoVersion saved = videoVersionRepository.save(version);

        // Update project status to IN_REVIEW or IN_PROGRESS
        project.setStatus(ProjectStatus.IN_REVIEW);
        projectRepository.save(project);

        return saved;
    }

    @Override
    public List<VideoVersion> getProjectVersions(Long projectId) {
        return videoVersionRepository.findByProjectIdOrderByVersionNumberDesc(projectId);
    }

    @Override
    public VideoVersion getVersionById(Long versionId) {
        return videoVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found with id: " + versionId));
    }

    @Override
    @Transactional
    public VideoVersion setActiveVersion(Long versionId) {
        VideoVersion targetVersion = getVersionById(versionId);
        Long projectId = targetVersion.getProject().getId();

        List<VideoVersion> versions = videoVersionRepository.findByProjectIdOrderByVersionNumberDesc(projectId);
        for (VideoVersion v : versions) {
            v.setActive(v.getId().equals(versionId));
            videoVersionRepository.save(v);
        }

        return targetVersion;
    }
}

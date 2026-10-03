package com.editorflow.service;

import com.editorflow.entity.VideoVersion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoVersionService {
    VideoVersion uploadVersion(Long projectId, MultipartFile file, String notes, Double durationInSeconds);
    List<VideoVersion> getProjectVersions(Long projectId);
    VideoVersion getVersionById(Long versionId);
    VideoVersion setActiveVersion(Long versionId);
}

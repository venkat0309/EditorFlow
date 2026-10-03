package com.editorflow.controller;

import com.editorflow.entity.VideoVersion;
import com.editorflow.service.VideoVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/versions")
@CrossOrigin(origins = "*")
public class VideoVersionController {

    private final VideoVersionService videoVersionService;

    public VideoVersionController(VideoVersionService videoVersionService) {
        this.videoVersionService = videoVersionService;
    }

    @PostMapping
    public ResponseEntity<VideoVersion> uploadVersion(
            @PathVariable Long projectId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "durationInSeconds", required = false) Double durationInSeconds) {
        VideoVersion version = videoVersionService.uploadVersion(projectId, file, notes, durationInSeconds);
        return ResponseEntity.ok(version);
    }

    @GetMapping
    public ResponseEntity<List<VideoVersion>> getProjectVersions(@PathVariable Long projectId) {
        return ResponseEntity.ok(videoVersionService.getProjectVersions(projectId));
    }

    @PutMapping("/{versionId}/activate")
    public ResponseEntity<VideoVersion> setActiveVersion(@PathVariable Long versionId) {
        return ResponseEntity.ok(videoVersionService.setActiveVersion(versionId));
    }
}

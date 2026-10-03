package com.editorflow.controller;

import com.editorflow.entity.*;
import com.editorflow.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public/review")
@CrossOrigin(origins = "*")
public class PublicReviewController {

    private final ReviewLinkService reviewLinkService;
    private final VideoVersionService videoVersionService;
    private final CommentService commentService;
    private final ApprovalService approvalService;

    public PublicReviewController(
            ReviewLinkService reviewLinkService,
            VideoVersionService videoVersionService,
            CommentService commentService,
            ApprovalService approvalService) {

        this.reviewLinkService = reviewLinkService;
        this.videoVersionService = videoVersionService;
        this.commentService = commentService;
        this.approvalService = approvalService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> getReviewData(
            @PathVariable String token) {

        ReviewLink link = reviewLinkService.getReviewLinkByToken(token);

        Project project = link.getProject();

        List<VideoVersion> versions = videoVersionService.getProjectVersions(project.getId());

        VideoVersion activeVersion = versions.stream()
                .filter(VideoVersion::getActive)
                .findFirst()
                .orElse(versions.isEmpty() ? null : versions.get(0));

        List<Comment> comments = activeVersion != null
                ? commentService.getVersionComments(activeVersion.getId())
                : List.of();

        Approval latestApproval = activeVersion != null
                ? approvalService.getLatestVersionApproval(activeVersion.getId())
                : null;

        Map<String, Object> response = new HashMap<>();

        response.put("link", link);

        // Convert Project entity to a simple Map
        // to avoid Hibernate Lazy Proxy serialization errors.
        Map<String, Object> projectData = new HashMap<>();
        projectData.put("id", project.getId());
        projectData.put("title", project.getTitle());
        projectData.put("description", project.getDescription());
        projectData.put("clientName", project.getClientName());
        projectData.put("status", project.getStatus());

        response.put("project", projectData);

        response.put("versions", versions);
        response.put("activeVersion", activeVersion);
        response.put("comments", comments);
        response.put("latestApproval", latestApproval);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{token}/comments")
    public ResponseEntity<Comment> addPublicComment(
            @PathVariable String token,
            @RequestBody Map<String, Object> payload) {

        ReviewLink link = reviewLinkService.getReviewLinkByToken(token);

        Long versionId = payload.get("versionId") != null
                ? Long.valueOf(payload.get("versionId").toString())
                : null;

        if (versionId == null) {

            List<VideoVersion> versions = videoVersionService.getProjectVersions(
                    link.getProject().getId());

            VideoVersion activeVersion = versions.stream()
                    .filter(VideoVersion::getActive)
                    .findFirst()
                    .orElse(null);

            if (activeVersion != null) {
                versionId = activeVersion.getId();
            } else {
                throw new RuntimeException(
                        "No active video version found for review");
            }
        }

        String authorName = (String) payload.get("authorName");

        Double timestampInSeconds = payload.get("timestampInSeconds") != null
                ? Double.valueOf(
                        payload.get("timestampInSeconds").toString())
                : 0.0;

        String text = (String) payload.get("text");

        Comment comment = commentService.addComment(
                versionId,
                authorName,
                "CLIENT",
                timestampInSeconds,
                text);

        return ResponseEntity.ok(comment);
    }

    @PostMapping("/{token}/approval")
    public ResponseEntity<Approval> submitPublicApproval(
            @PathVariable String token,
            @RequestBody Map<String, String> payload) {

        ReviewLink link = reviewLinkService.getReviewLinkByToken(token);

        String versionIdStr = payload.get("versionId");

        Long versionId = versionIdStr != null
                ? Long.valueOf(versionIdStr)
                : null;

        if (versionId == null) {

            List<VideoVersion> versions = videoVersionService.getProjectVersions(
                    link.getProject().getId());

            VideoVersion activeVersion = versions.stream()
                    .filter(VideoVersion::getActive)
                    .findFirst()
                    .orElse(null);

            if (activeVersion != null) {
                versionId = activeVersion.getId();
            } else {
                throw new RuntimeException(
                        "No active video version found for review");
            }
        }

        String clientName = payload.get("clientName");

        String clientEmail = payload.get("clientEmail");

        String status = payload.get("status");

        String feedbackNotes = payload.get("feedbackNotes");

        return ResponseEntity.ok(
                approvalService.submitApproval(
                        versionId,
                        clientName,
                        clientEmail,
                        status,
                        feedbackNotes));
    }
}
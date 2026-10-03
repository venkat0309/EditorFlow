package com.editorflow.controller;

import com.editorflow.entity.ReviewLink;
import com.editorflow.service.ReviewLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/review-links")
@CrossOrigin(origins = "*")
public class ReviewLinkController {

    private final ReviewLinkService reviewLinkService;

    public ReviewLinkController(ReviewLinkService reviewLinkService) {
        this.reviewLinkService = reviewLinkService;
    }

    @PostMapping
    public ResponseEntity<ReviewLink> createReviewLink(
            @PathVariable Long projectId,
            @RequestBody(required = false) Map<String, Object> payload) {
        String passcode = payload != null ? (String) payload.get("passcode") : null;
        Integer validDays = payload != null && payload.get("validDays") != null ?
                Integer.parseInt(payload.get("validDays").toString()) : 30;

        return ResponseEntity.ok(reviewLinkService.createReviewLink(projectId, passcode, validDays));
    }

    @GetMapping
    public ResponseEntity<List<ReviewLink>> getProjectReviewLinks(@PathVariable Long projectId) {
        return ResponseEntity.ok(reviewLinkService.getProjectReviewLinks(projectId));
    }

    @PutMapping("/{linkId}/deactivate")
    public ResponseEntity<Void> deactivateReviewLink(@PathVariable Long linkId) {
        reviewLinkService.deactivateReviewLink(linkId);
        return ResponseEntity.noContent().build();
    }
}

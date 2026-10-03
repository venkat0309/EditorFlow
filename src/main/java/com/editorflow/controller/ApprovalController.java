package com.editorflow.controller;

import com.editorflow.entity.Approval;
import com.editorflow.service.ApprovalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/versions/{versionId}/approvals")
@CrossOrigin(origins = "*")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping
    public ResponseEntity<Approval> submitApproval(
            @PathVariable Long versionId,
            @RequestBody Map<String, String> payload) {
        String clientName = payload.get("clientName");
        String clientEmail = payload.get("clientEmail");
        String status = payload.get("status"); // APPROVED or REVISION_REQUESTED
        String feedbackNotes = payload.get("feedbackNotes");

        return ResponseEntity.ok(approvalService.submitApproval(versionId, clientName, clientEmail, status, feedbackNotes));
    }

    @GetMapping
    public ResponseEntity<List<Approval>> getVersionApprovals(@PathVariable Long versionId) {
        return ResponseEntity.ok(approvalService.getVersionApprovals(versionId));
    }

    @GetMapping("/latest")
    public ResponseEntity<Approval> getLatestVersionApproval(@PathVariable Long versionId) {
        return ResponseEntity.ok(approvalService.getLatestVersionApproval(versionId));
    }
}

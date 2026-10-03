package com.editorflow.service;

import com.editorflow.entity.Approval;

import java.util.List;

public interface ApprovalService {
    Approval submitApproval(Long versionId, String clientName, String clientEmail, String status, String feedbackNotes);
    List<Approval> getVersionApprovals(Long versionId);
    Approval getLatestVersionApproval(Long versionId);
}

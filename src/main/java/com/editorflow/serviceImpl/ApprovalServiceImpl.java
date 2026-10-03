package com.editorflow.serviceImpl;

import com.editorflow.entity.Approval;
import com.editorflow.entity.Project;
import com.editorflow.entity.ProjectStatus;
import com.editorflow.entity.VideoVersion;
import com.editorflow.repository.ApprovalRepository;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.VideoVersionRepository;
import com.editorflow.service.ApprovalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final VideoVersionRepository videoVersionRepository;
    private final ProjectRepository projectRepository;

    public ApprovalServiceImpl(ApprovalRepository approvalRepository,
                               VideoVersionRepository videoVersionRepository,
                               ProjectRepository projectRepository) {
        this.approvalRepository = approvalRepository;
        this.videoVersionRepository = videoVersionRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public Approval submitApproval(Long versionId, String clientName, String clientEmail, String status, String feedbackNotes) {
        VideoVersion version = videoVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found with id: " + versionId));

        Approval.ApprovalStatus approvalStatus = "APPROVED".equalsIgnoreCase(status) ?
                Approval.ApprovalStatus.APPROVED : Approval.ApprovalStatus.REVISION_REQUESTED;

        Approval approval = new Approval();
        approval.setVersion(version);
        approval.setClientName(clientName != null ? clientName : "Client");
        approval.setClientEmail(clientEmail);
        approval.setStatus(approvalStatus);
        approval.setFeedbackNotes(feedbackNotes);

        Approval saved = approvalRepository.save(approval);

        // Update parent project status
        Project project = version.getProject();
        if (approvalStatus == Approval.ApprovalStatus.APPROVED) {
            project.setStatus(ProjectStatus.APPROVED);
        } else {
            project.setStatus(ProjectStatus.IN_PROGRESS);
        }
        projectRepository.save(project);

        return saved;
    }

    @Override
    public List<Approval> getVersionApprovals(Long versionId) {
        return approvalRepository.findByVersionId(versionId);
    }

    @Override
    public Approval getLatestVersionApproval(Long versionId) {
        return approvalRepository.findTopByVersionIdOrderByCreatedAtDesc(versionId).orElse(null);
    }
}

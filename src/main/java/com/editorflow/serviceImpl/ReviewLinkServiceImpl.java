package com.editorflow.serviceImpl;

import com.editorflow.entity.Project;
import com.editorflow.entity.ReviewLink;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.ReviewLinkRepository;
import com.editorflow.service.ReviewLinkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewLinkServiceImpl implements ReviewLinkService {

    private final ReviewLinkRepository reviewLinkRepository;
    private final ProjectRepository projectRepository;

    public ReviewLinkServiceImpl(ReviewLinkRepository reviewLinkRepository, ProjectRepository projectRepository) {
        this.reviewLinkRepository = reviewLinkRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ReviewLink createReviewLink(Long projectId, String passcode, Integer validDays) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        ReviewLink reviewLink = new ReviewLink();
        reviewLink.setProject(project);
        reviewLink.setToken(token);
        reviewLink.setPasscode(passcode);
        reviewLink.setExpiresAt(validDays != null ? LocalDateTime.now().plusDays(validDays) : LocalDateTime.now().plusDays(30));
        reviewLink.setActive(true);
        reviewLink.setAccessCount(0);

        return reviewLinkRepository.save(reviewLink);
    }

    @Override
    public ReviewLink getReviewLinkByToken(String token) {
        ReviewLink reviewLink = reviewLinkRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired review link token"));

        if (!reviewLink.getActive()) {
            throw new RuntimeException("This review link has been deactivated");
        }

        if (reviewLink.getExpiresAt() != null && reviewLink.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("This review link has expired");
        }

        reviewLink.setAccessCount(reviewLink.getAccessCount() + 1);
        return reviewLinkRepository.save(reviewLink);
    }

    @Override
    public List<ReviewLink> getProjectReviewLinks(Long projectId) {
        return reviewLinkRepository.findByProjectId(projectId);
    }

    @Override
    public void deactivateReviewLink(Long linkId) {
        ReviewLink link = reviewLinkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Review link not found"));
        link.setActive(false);
        reviewLinkRepository.save(link);
    }
}

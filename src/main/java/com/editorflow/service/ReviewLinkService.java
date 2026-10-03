package com.editorflow.service;

import com.editorflow.entity.ReviewLink;

import java.util.List;

public interface ReviewLinkService {
    ReviewLink createReviewLink(Long projectId, String passcode, Integer validDays);
    ReviewLink getReviewLinkByToken(String token);
    ReviewLink getReviewLinkByToken(String token, String passcode);
    List<ReviewLink> getProjectReviewLinks(Long projectId);
    void deactivateReviewLink(Long linkId);
}

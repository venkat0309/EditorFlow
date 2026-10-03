package com.editorflow.service;

import com.editorflow.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long versionId, String authorName, String authorRole, Double timestampInSeconds, String text);
    List<Comment> getVersionComments(Long versionId);
    Comment toggleResolveComment(Long commentId);
    void deleteComment(Long commentId);
}

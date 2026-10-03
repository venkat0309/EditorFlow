package com.editorflow.serviceImpl;

import com.editorflow.entity.Comment;
import com.editorflow.entity.VideoVersion;
import com.editorflow.repository.CommentRepository;
import com.editorflow.repository.VideoVersionRepository;
import com.editorflow.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final VideoVersionRepository videoVersionRepository;

    public CommentServiceImpl(CommentRepository commentRepository, VideoVersionRepository videoVersionRepository) {
        this.commentRepository = commentRepository;
        this.videoVersionRepository = videoVersionRepository;
    }

    @Override
    public Comment addComment(Long versionId, String authorName, String authorRole, Double timestampInSeconds, String text) {
        VideoVersion version = videoVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found with id: " + versionId));

        Comment comment = new Comment();
        comment.setVersion(version);
        comment.setAuthorName(authorName != null ? authorName : "Reviewer");
        comment.setAuthorRole(authorRole != null ? authorRole : "CLIENT");
        comment.setTimestampInSeconds(timestampInSeconds != null ? timestampInSeconds : 0.0);
        comment.setText(text);
        comment.setResolved(false);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getVersionComments(Long versionId) {
        return commentRepository.findByVersionIdOrderByTimestampInSecondsAsc(versionId);
    }

    @Override
    public Comment toggleResolveComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        comment.setResolved(!comment.getResolved());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

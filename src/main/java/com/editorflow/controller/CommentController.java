package com.editorflow.controller;

import com.editorflow.entity.Comment;
import com.editorflow.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/versions/{versionId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @PathVariable Long versionId,
            @RequestBody Map<String, Object> payload) {
        String authorName = (String) payload.get("authorName");
        String authorRole = (String) payload.get("authorRole");
        Double timestampInSeconds = payload.get("timestampInSeconds") != null ?
                Double.valueOf(payload.get("timestampInSeconds").toString()) : 0.0;
        String text = (String) payload.get("text");

        Comment comment = commentService.addComment(versionId, authorName, authorRole, timestampInSeconds, text);
        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getVersionComments(@PathVariable Long versionId) {
        return ResponseEntity.ok(commentService.getVersionComments(versionId));
    }

    @PutMapping("/comments/{commentId}/toggle-resolve")
    public ResponseEntity<Comment> toggleResolveComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.toggleResolveComment(commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

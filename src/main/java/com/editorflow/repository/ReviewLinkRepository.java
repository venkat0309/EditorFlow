package com.editorflow.repository;

import com.editorflow.entity.ReviewLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewLinkRepository extends JpaRepository<ReviewLink, Long> {
    Optional<ReviewLink> findByToken(String token);
    List<ReviewLink> findByProjectId(Long projectId);
}

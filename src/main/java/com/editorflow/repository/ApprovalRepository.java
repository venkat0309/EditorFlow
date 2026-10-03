package com.editorflow.repository;

import com.editorflow.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByVersionId(Long versionId);
    Optional<Approval> findTopByVersionIdOrderByCreatedAtDesc(Long versionId);
}

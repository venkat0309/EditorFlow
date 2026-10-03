package com.editorflow.repository;

import com.editorflow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByAssignedEditorIdOrderByCreatedAtDesc(Long assignedEditorId);
}

package com.editorflow.repository;

import com.editorflow.entity.VideoVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoVersionRepository extends JpaRepository<VideoVersion, Long> {
    List<VideoVersion> findByProjectIdOrderByVersionNumberDesc(Long projectId);
    Integer countByProjectId(Long projectId);
}

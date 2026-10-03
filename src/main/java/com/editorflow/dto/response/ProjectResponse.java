package com.editorflow.dto.response;

//package com.editorflow.dto.response;

import com.editorflow.entity.ProjectStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;

    private String title;

    private String description;

    private String clientName;

    private String clientEmail;

    private LocalDate dueDate;

    private Long assignedEditorId;

    private String assignedEditorName;

    private String assignedEditorEmail;

    private ProjectStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ProjectResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getAssignedEditorId() {
        return assignedEditorId;
    }

    public void setAssignedEditorId(Long assignedEditorId) {
        this.assignedEditorId = assignedEditorId;
    }

    public String getAssignedEditorName() {
        return assignedEditorName;
    }

    public void setAssignedEditorName(String assignedEditorName) {
        this.assignedEditorName = assignedEditorName;
    }

    public String getAssignedEditorEmail() {
        return assignedEditorEmail;
    }

    public void setAssignedEditorEmail(String assignedEditorEmail) {
        this.assignedEditorEmail = assignedEditorEmail;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package com.editorflow.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProjectRequest {

    @NotBlank(message = "Project title is required")
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Client name is required")
    @Size(max = 100)
    private String clientName;

    @Email(message = "Please provide valid client email")
    private String clientEmail;

    private LocalDate dueDate;

    private Long assignedEditorId;

    public CreateProjectRequest() {
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
}

package com.editorflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProjectRequest {

    @NotBlank(message = "Project title is required")
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Client name is required")
    @Size(max = 100)
    private String clientName;

    public UpdateProjectRequest() {

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

}

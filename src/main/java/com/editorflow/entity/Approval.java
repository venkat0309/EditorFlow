package com.editorflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "approvals")
public class Approval extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private VideoVersion version;

    @Column(nullable = false)
    private String clientName;

    private String clientEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;

    @Column(length = 2000)
    private String feedbackNotes;

    public Approval() {
    }

    public enum ApprovalStatus {
        APPROVED,
        REVISION_REQUESTED
    }

    public Long getId() {
        return id;
    }

    public VideoVersion getVersion() {
        return version;
    }

    public void setVersion(VideoVersion version) {
        this.version = version;
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

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getFeedbackNotes() {
        return feedbackNotes;
    }

    public void setFeedbackNotes(String feedbackNotes) {
        this.feedbackNotes = feedbackNotes;
    }
}

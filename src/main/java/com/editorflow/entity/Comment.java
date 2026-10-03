package com.editorflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private VideoVersion version;

    @Column(nullable = false)
    private String authorName;

    private String authorRole; // e.g. CLIENT, EDITOR

    @Column(nullable = false)
    private Double timestampInSeconds;

    @Column(nullable = false, length = 2000)
    private String text;

    @Column(nullable = false)
    private Boolean resolved = false;

    public Comment() {
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorRole() {
        return authorRole;
    }

    public void setAuthorRole(String authorRole) {
        this.authorRole = authorRole;
    }

    public Double getTimestampInSeconds() {
        return timestampInSeconds;
    }

    public void setTimestampInSeconds(Double timestampInSeconds) {
        this.timestampInSeconds = timestampInSeconds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}

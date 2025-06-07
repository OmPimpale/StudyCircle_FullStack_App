package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime; // Import LocalDateTime

@Entity
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @CreationTimestamp
    private LocalDateTime uploadTimestamp; // Use java.time.LocalDateTime

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public LocalDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(LocalDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public Resource() {
    }

    public Resource(Long id, String fileName, String fileType, String url, User uploader, LocalDateTime uploadTimestamp, Session session) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.url = url;
        this.uploader = uploader;
        this.uploadTimestamp = uploadTimestamp;
        this.session = session;
    }
}

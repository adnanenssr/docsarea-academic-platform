package com.docsarea.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    String username ;
    String fileId ;
    LocalDateTime savedAt ;

    public Bookmark() {
    }

    public Bookmark(String username, String fileId) {
        this.username = username;
        this.fileId = fileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    @PrePersist
    public void setSavedAt() {
        this.savedAt = LocalDateTime.now();
    }
}

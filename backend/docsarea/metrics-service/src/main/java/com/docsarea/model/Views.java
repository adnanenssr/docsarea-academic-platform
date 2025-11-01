package com.docsarea.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Views {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String fileId ;
    String publisher ;
    String owner ;
    Long count ;
    LocalDateTime week ;
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt ;
    LocalDateTime updatedAt ;


    public Views() {}

    public Views(String fileId , LocalDateTime week , String publisher , String owner) {
        this.fileId = fileId;
        this.count = 1L;
        this.week = week;
        this.publisher = publisher ;
        this.owner = owner ;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDateTime getWeek() {
        return week;
    }

    public void setWeek(LocalDateTime week) {
        this.week = week;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now() ;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}

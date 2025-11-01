package com.docsarea.module;

import com.docsarea.enums.Status;
import com.docsarea.utility.GenerateUlid;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FileReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    String groupId ;
    String fileId ;
    String moderator ;
    @Enumerated(EnumType.STRING)
    Status status ;
    String comment ;
    boolean isLocked ;
    LocalDateTime createdAt ;
    LocalDateTime reviewedAt ;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderatorId) {
        this.moderator = moderatorId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    @PreUpdate
    public void setReviewedAt() {
        this.reviewedAt = LocalDateTime.now();
    }


}

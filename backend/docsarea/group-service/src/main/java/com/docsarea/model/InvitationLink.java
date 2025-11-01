package com.docsarea.model;

import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;
import com.docsarea.utility.GenerateUlid;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class InvitationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    String description ;
    String createdBy ;
    String groupId ;
    LocalDateTime createdAt ;
    LocalDateTime expiresAt ;


    boolean isActive ;

    @Enumerated (EnumType.STRING)
    GroupRoles role ;

    boolean reviewFile ;
    boolean reviewJoinRequest ;
    boolean invitePermission ;

    @Enumerated(EnumType.STRING)
    FileUploadPermission uploadPermission ;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public GroupRoles getRole() {
        return role;
    }

    public void setRole(GroupRoles role) {
        this.role = role;
    }

    public boolean isReviewFile() {
        return reviewFile;
    }

    public void setReviewFile(boolean reviewFile) {
        this.reviewFile = reviewFile;
    }

    public boolean isReviewJoinRequest() {
        return reviewJoinRequest;
    }

    public void setReviewJoinRequest(boolean reviewJoinRequest) {
        this.reviewJoinRequest = reviewJoinRequest;
    }

    public boolean isInvitePermission() {
        return invitePermission;
    }

    public void setInvitePermission(boolean invitePermission) {
        this.invitePermission = invitePermission;
    }

    public FileUploadPermission getUploadPermission() {
        return uploadPermission;
    }

    public void setUploadPermission(FileUploadPermission uploadPermission) {
        this.uploadPermission = uploadPermission;
    }
}

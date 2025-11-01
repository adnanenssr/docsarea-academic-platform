package com.docsarea.dtos.invitation;

import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class CreateInvitationLinkDto {
    String groupId ;
    String Description ;

    LocalDateTime expiresAt ;

    boolean isActive ;

    @Enumerated(EnumType.STRING)
    GroupRoles role ;

    boolean reviewFile ;
    boolean reviewJoinRequest ;
    boolean invitePermission ;

    @Enumerated(EnumType.STRING)
    FileUploadPermission uploadPermission ;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

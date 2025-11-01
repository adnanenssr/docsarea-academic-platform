package com.docsarea.model;

import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;
import com.docsarea.utility.GenerateUlid;
import jakarta.persistence.*;
import jakarta.validation.Constraint;

import java.time.LocalDateTime;

@Entity
@IdClass(MemberPk.class)
public class Member {
    @Id
    String username ;
    @Id
    String groupId ;
    @Enumerated(EnumType.STRING)
    GroupRoles role ;
    boolean reviewFile ;
    boolean reviewJoinRequest ;
    boolean invitePermission ;
    @Enumerated(EnumType.STRING)
    FileUploadPermission uploadPermission ;

    LocalDateTime joinedAt ;
    LocalDateTime modifiedAt ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    @PrePersist
    public void setJoinedAt() {
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @PreUpdate
    public void setModifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }





    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

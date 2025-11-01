package com.docsarea.dtos.member;

import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;

public class MemberDto {
    String username ;
    String groupId ;
    GroupRoles role ;

    boolean reviewFile ;
    boolean reviewJoinRequest ;
    boolean invitePermission ;

    FileUploadPermission uploadPermission ;

    public MemberDto(String username, GroupRoles role, boolean reviewFile, boolean reviewJoinRequest, boolean invitePermission, FileUploadPermission uploadPermission) {
        this.username = username;
        this.role = role;
        this.reviewFile = reviewFile;
        this.reviewJoinRequest = reviewJoinRequest;
        this.invitePermission = invitePermission;
        this.uploadPermission = uploadPermission;
    }

    public MemberDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

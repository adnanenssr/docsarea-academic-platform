package com.docsarea.dtos.member;

import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;

public class UpdateMemberDto {


    GroupRoles role ;
    boolean reviewFile ;
    boolean reviewJoinRequest ;
    boolean invitePermission ;

    FileUploadPermission uploadPermission ;



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

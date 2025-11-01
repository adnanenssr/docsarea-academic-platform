package com.docsarea.service;

import com.docsarea.dtos.invitation.CreateInvitationLinkDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.member.UpdateMemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Member;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Currency;

@Service
public class UpdateMemberService {

    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper();

    public void isAdmin(Member member){
        if(!member.getRole().isHigherThan(GroupRoles.MODERATOR)) throw new RuntimeException("you don't have permissions to modify a member") ;
    }

    public Member getMember(String groupId , String username){
        return memberRepo.findByGroupIdAndUsername(groupId , username).orElseThrow(() -> new RuntimeException("the user you are trying to update is not a member of the group ")) ;
    }
    public void verifyPermissions (UpdateMemberDto dto , Member member , String groupId){

        if(!member.isInvitePermission()) throw new RuntimeException("you don't have permission to invite users to this group") ;
        if(!member.getRole().isHigherThan(dto.getRole())) throw new RuntimeException("you can not grand roles equal or higher than yours") ;
        if(dto.getRole() == GroupRoles.MEMBER && (dto.isReviewFile() || dto.isReviewJoinRequest() || dto.isInvitePermission())) throw new RuntimeException("a member can not have review permissions") ;
        if(dto.getRole() == GroupRoles.MODERATOR && !(dto.isReviewFile() || dto.isReviewJoinRequest())) throw new RuntimeException("Moderator should have at least one moderator permission ") ;
        if(!dto.getRole().isHigherThan(GroupRoles.MODERATOR) && (dto.isInvitePermission())) throw new RuntimeException("only admins can have invite permission ") ;

    }

    public MemberDto updateMember(UpdateMemberDto dto , String groupId, String username){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Member thisUser = memberRepo.findByGroupIdAndUsername(groupId , user).orElseThrow(() -> new RuntimeException("you are not member of this group")) ;
        isAdmin(thisUser);
        verifyPermissions(dto , thisUser , groupId);
        Member member = getMember(groupId ,username) ;
        member.setGroupId(groupId);
        member.setRole(dto.getRole());
        member.setInvitePermission(dto.isInvitePermission());
        member.setReviewFile(dto.isReviewFile());
        member.setUploadPermission(dto.getUploadPermission());
        member.setReviewJoinRequest(dto.isReviewJoinRequest());
        Member updated = memberRepo.save(member) ;
        return modelMapper.map(updated , MemberDto.class) ;
    }







}

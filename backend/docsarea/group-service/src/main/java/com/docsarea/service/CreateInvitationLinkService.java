package com.docsarea.service;

import com.docsarea.dtos.invitation.CreateInvitationLinkDto;
import com.docsarea.dtos.invitation.GetInvitationLink;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.InvitationLink;
import com.docsarea.model.Member;
import com.docsarea.repository.InvitationLinkRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateInvitationLinkService {

    @Autowired
    InvitationLinkRepo linkRepo ;
    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public Member verifyUser(String username , String groupId){
        return memberRepo.findByGroupIdAndUsername(groupId , username).orElseThrow(() -> new RuntimeException("you are not a member of this group")) ;    }

    public void verifyPermissions (CreateInvitationLinkDto dto , String username , String groupId , Member member){

        if(!member.isInvitePermission()) throw new RuntimeException("you don't have permission to invite users to this group") ;
        if(!member.getRole().isHigherThan(dto.getRole())) throw new RuntimeException("you can not grand roles equal or higher than yours") ;
        if(dto.getRole() == GroupRoles.MEMBER && (dto.isReviewFile() || dto.isReviewJoinRequest() || dto.isInvitePermission())) throw new RuntimeException("a member can not have review permissions") ;
        if(dto.getRole() == GroupRoles.MODERATOR && !(dto.isReviewFile() || dto.isReviewJoinRequest())) throw new RuntimeException("Moderator should have at least one moderator permission ") ;
        if(!dto.getRole().isHigherThan(GroupRoles.MODERATOR) && (dto.isInvitePermission())) throw new RuntimeException("only admins can have invite permission ") ;

    }

    public void verifyExpiration(LocalDateTime expiresAt){
        if(expiresAt.isBefore(LocalDateTime.now())) throw new RuntimeException("please set a valid expiration time") ;
    }

    public InvitationLink getLink (CreateInvitationLinkDto dto , String groupId , String username){
        InvitationLink link = modelMapper.map(dto , InvitationLink.class) ;
        link.setCreatedBy(username);
        link.setGroupId(groupId);
        return link ;
    }

    public GetInvitationLink addLink(CreateInvitationLinkDto dto , String groupId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Member member = verifyUser(user , groupId);
        verifyPermissions(dto , user , groupId , member);
        verifyExpiration(dto.getExpiresAt());
        InvitationLink link = getLink(dto, groupId, user) ;
        link.setActive(true);
        InvitationLink createdLink = linkRepo.save(link) ;
        return modelMapper.map(createdLink , GetInvitationLink.class) ;
    }


}

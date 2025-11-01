package com.docsarea.service;

import com.docsarea.dtos.group.GetGroupDto;
import com.docsarea.dtos.group.GroupInfoDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Group;
import com.docsarea.model.InvitationLink;
import com.docsarea.model.Member;
import com.docsarea.repository.GroupRepo;
import com.docsarea.repository.InvitationLinkRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvitationLinkService {
    @Autowired
    InvitationLinkRepo linkRepo ;
    @Autowired
    GroupRepo groupRepo ;
    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public InvitationLink getLink(String id){
        return linkRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found url")) ;
    }

    public GroupInfoDto linkGroup(String groupId){

        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        return modelMapper.map(group , GroupInfoDto.class) ;
    }

    public void isAuthenticated(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication() ;
        if( !(auth instanceof JwtAuthenticationToken)) throw new RuntimeException("you should have an account to continue") ;
    }

    public void isExpired (LocalDateTime expiration){
        if(!LocalDateTime.now().isBefore(expiration)) throw new RuntimeException("this link is expired") ;
    }

    public void isActive(boolean isActive){
        if(!isActive) throw new RuntimeException("url is disabled") ;
    }

    public void isMember(String groupId){
        if(memberRepo.existsByUsernameAndGroupId(CurrentAuthenticatedUser.getCurrentAuthenticatedUser() , groupId)) throw new RuntimeException("you are already a member") ;
    }

    public GroupInfoDto joinGroup(String id){
        InvitationLink link = getLink(id);
        isActive(link.isActive());
        isMember(link.getGroupId());
        isExpired(link.getExpiresAt());
        isAuthenticated();
        return linkGroup(link.getGroupId()) ;
    }

    public GetGroupDto addMember(String id){
        InvitationLink link = getLink(id);
        isActive(link.isActive());
        isMember(link.getGroupId());
        isExpired(link.getExpiresAt());
        isAuthenticated();

        Member member = new Member() ;
        member.setReviewJoinRequest(link.isReviewJoinRequest());
        member.setUploadPermission(link.getUploadPermission());
        member.setRole(link.getRole());
        member.setGroupId(link.getGroupId());
        member.setInvitePermission(link.isInvitePermission());
        member.setReviewFile(link.isReviewFile());
        member.setUsername(CurrentAuthenticatedUser.getCurrentAuthenticatedUser());
        Member added = memberRepo.save(member) ;
        return modelMapper.map(added , GetGroupDto.class) ;
    }





}

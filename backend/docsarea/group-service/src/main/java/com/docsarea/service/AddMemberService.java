package com.docsarea.service;

import com.docsarea.controller.GroupUserFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Member;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AddMemberService {

    @Autowired
    MemberRepo memberRepo ;
    @Autowired
    GroupUserFeignController userFeignController ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;



    public boolean verifyAdmin( String id ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser()  ;
        Member member = memberRepo.findByGroupIdAndUsername(id , user).orElseThrow(() -> new RuntimeException("you are not a member of this group")) ;
        return member.getRole().isHigherThan(GroupRoles.MODERATOR) ;
    }
    public boolean verifyUserExist(String username){
        Set<String> user = Set.of(username) ;
        return userFeignController.isExist(user) ;
    }

    public boolean selfUpdate(String username){
        return CurrentAuthenticatedUser.getCurrentAuthenticatedUser().equals(username) ;
    }


    public MemberDto addMember(MemberDto dto , String groupId){
        if(!verifyAdmin(groupId )) throw new RuntimeException("you are not admin of this group") ;
        if(!verifyUserExist(dto.getUsername())) throw new RuntimeException("the username you provided does not match any existing user") ;
        if(selfUpdate(dto.getUsername())) throw new RuntimeException("you can't update your own membership") ;
        if(dto.getRole() == GroupRoles.MEMBER && (dto.isReviewFile() || dto.isReviewJoinRequest() || dto.isInvitePermission())) throw new RuntimeException("a member can not have review permissions") ;
        if(dto.getRole() == GroupRoles.MODERATOR && !(dto.isReviewFile() || dto.isReviewJoinRequest())) throw new RuntimeException("Moderator should have at least one moderator permission ") ;
        if(!dto.getRole().isHigherThan(GroupRoles.MODERATOR) && (dto.isInvitePermission())) throw new RuntimeException("only admins can have invite permission ") ;

        Member member = modelMapper.map(dto , Member.class) ;
        member.setGroupId(groupId);
        Member added = memberRepo.save(member) ;
        return modelMapper.map(added , MemberDto.class) ;
    }



}

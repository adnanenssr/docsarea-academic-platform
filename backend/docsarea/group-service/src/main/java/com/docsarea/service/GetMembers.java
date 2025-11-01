package com.docsarea.service;

import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Member;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMembers {
    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    public List<String> getMembers(String groupId , GroupRoles role){
        return memberRepo.findMembersByGroupIdAndRole(groupId , role) ;
    }
    public MemberDto getMember(String groupId , String username){
        return memberRepo.findByGroupIdAndUsername(groupId , username)
                .map(member -> modelMapper.map(member , MemberDto.class))
                .orElseThrow(() -> new RuntimeException("member does not exist")) ;
    }

    public MemberDto getThisMember(String groupId) {
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        return getMember(groupId , user) ;
    }
}

package com.docsarea.service;

import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.member.MemberPageDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Member;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMembers {
    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public void isMember(String username , String groupId){
        memberRepo.findByGroupIdAndUsername(groupId , username).orElseThrow(() -> new RuntimeException("you are not member of this group")) ;
    }

    public MemberPageDto getAllMembers(String groupId , int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        isMember(user , groupId);
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<Member> membersPage = memberRepo.findByGroupId(groupId , pageable) ;
        MemberPageDto membersDto = new MemberPageDto() ;
        List<MemberDto> members = membersPage.stream().map((member) -> modelMapper.map(member , MemberDto.class)).toList() ;
        membersDto.setMembers(members);
        membersDto.setNumPages(membersPage.getTotalPages());
        membersDto.setNumElements(membersPage.getTotalElements());
        return membersDto ;
    }

}

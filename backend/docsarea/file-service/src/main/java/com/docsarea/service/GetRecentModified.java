package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRecentModified {
    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    public List<GetFileDto> userRecentModified(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(0,7) ;
        Page<Document> docs = fileRepo.getUserRecentModified(user , pageable) ;
        return docs.getContent().stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;
    }

    public List<GetFileDto> groupRecentModified(String groupId){
        isMember(groupId);
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(0,7) ;
        Page<Document> docs = fileRepo.getGroupRecentModified(groupId , pageable) ;
        return docs.getContent().stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;
    }

    public void  isMember (String groupId){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = groupFeignController.getMember(groupId , username) ;
    }



}

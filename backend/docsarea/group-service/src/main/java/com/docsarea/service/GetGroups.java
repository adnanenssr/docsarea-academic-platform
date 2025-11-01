package com.docsarea.service;

import com.docsarea.dtos.group.GroupInfoDto;
import com.docsarea.dtos.group.GroupPageDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Group;
import com.docsarea.repository.GroupRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGroups {
    @Autowired
    GroupRepo groupRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public GroupPageDto getGroups(int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page, 20) ;
        Page<Group> grpPage = groupRepo.findByOwner(user , pageable);
        List<GroupInfoDto> groups = grpPage.stream().map(grp -> modelMapper.map(grp , GroupInfoDto.class)).toList() ;
        GroupPageDto pageDto = new GroupPageDto() ;
        pageDto.setGroups(groups);
        pageDto.setNumPages(grpPage.getTotalPages());
        pageDto.setNumElements(grpPage.getTotalElements());
        return pageDto ;

    }
}

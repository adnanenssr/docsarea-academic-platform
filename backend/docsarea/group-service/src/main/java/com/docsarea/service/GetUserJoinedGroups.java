package com.docsarea.service;

import com.docsarea.dtos.group.GroupInfoDto;
import com.docsarea.dtos.group.GroupPageDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Group;
import com.docsarea.model.Member;
import com.docsarea.repository.GroupRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserJoinedGroups {
    @Autowired
    MemberRepo memberRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    GroupRepo groupRepo ;

    public GroupPageDto getUserJoinedGroups(int page){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<Member> memberships = memberRepo.findByUsernameAndRoleIsNot(username , GroupRoles.OWNER , pageable);
        List<String> groups = memberships.stream().map(Member::getGroupId).toList() ;
        List<GroupInfoDto> groupsDto = groupRepo.findAllById(groups).stream().map(group -> modelMapper.map(group , GroupInfoDto.class)).toList() ;
        GroupPageDto groupPageDto = new GroupPageDto() ;
        groupPageDto.setNumPages(memberships.getTotalPages());
        groupPageDto.setNumElements(memberships.getTotalElements());
        groupPageDto.setGroups(groupsDto);
        return groupPageDto ;
    }
}

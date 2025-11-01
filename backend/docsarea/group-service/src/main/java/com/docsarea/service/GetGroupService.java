package com.docsarea.service;

import com.docsarea.dtos.group.GroupInfoDto;
import com.docsarea.enums.GroupPrivacy;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Group;
import com.docsarea.model.Member;
import com.docsarea.repository.GroupRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetGroupService {
    @Autowired
    GroupRepo groupRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    MemberRepo memberRepo ;

    public void verifyPrivacy(String groupId ,GroupPrivacy privacy){
        if(privacy == GroupPrivacy.PUBLIC) return ;
        boolean isMember = memberRepo.existsByUsernameAndGroupId(CurrentAuthenticatedUser.getCurrentAuthenticatedUser() , groupId) ;
        if(privacy == GroupPrivacy.PRIVATE && !isMember) throw new RuntimeException("you can't see this group ") ;
        return ;
    }



    public GroupInfoDto getGroup (String groupId){
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException( "this group does not exists")) ;
        verifyPrivacy(groupId , group.getPrivacy());
        return  modelMapper.map(group , GroupInfoDto.class) ;
    }
}

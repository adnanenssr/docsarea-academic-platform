package com.docsarea.service;

import com.docsarea.controller.FileFeignController;
import com.docsarea.enums.GroupRoles;
import com.docsarea.model.Group;
import com.docsarea.model.Member;
import com.docsarea.repository.GroupRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DeleteGroup {
    @Autowired
    GroupRepo groupRepo ;
    @Autowired
    MemberRepo memberRepo ;
    @Autowired
    FileFeignController fileFeignController ;

    public void deleteGroup(String groupId){
        Member owner = memberRepo.findByGroupIdAndUsername( groupId , CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ).orElseThrow(() -> new RuntimeException("you are not member of this group")) ;
        if(owner.getRole() != GroupRoles.OWNER) throw new RuntimeException("you don't have permission to delete this group") ;
        Pageable pageable = PageRequest.of(0 , 20) ;
        Page<Member> members = memberRepo.findByGroupId(groupId , pageable) ;
        while (members.getTotalElements() > 0){
            members.forEach(member -> fileFeignController.transferFilesToUser(member.getUsername() , groupId));
            memberRepo.deleteAll(members);
            members = memberRepo.findByGroupId(groupId , pageable) ;
        }
        groupRepo.deleteById(groupId);
    }
    public void deleteAllUserGroups(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser();
        Pageable pageable = PageRequest.of(0 , 20) ;
        Page<Group> groups = groupRepo.findByOwner(user , pageable) ;
        while (groups.getTotalElements() > 0){
            groupRepo.deleteAll(groups);
            groups = groupRepo.findByOwner(user , pageable) ;
        }


    }
}

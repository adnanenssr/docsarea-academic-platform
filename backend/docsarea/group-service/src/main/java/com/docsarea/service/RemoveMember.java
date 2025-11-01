package com.docsarea.service;

import com.docsarea.controller.FileFeignController;
import com.docsarea.enums.GroupRoles;
import com.docsarea.enums.OnMemberRemovalStrategy;
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
public class RemoveMember {
    @Autowired
    GroupRepo groupRepo ;
    @Autowired
    MemberRepo memberRepo ;
    @Autowired
    FileFeignController fileFeignController ;

    public void removeMember(String username , String groupId){
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Member member = memberRepo.findByGroupIdAndUsername(groupId , username).orElseThrow(() -> new RuntimeException("the member you are trying to remove does not exist")) ;
        Member admin = memberRepo.findByGroupIdAndUsername(groupId , user).orElseThrow(() -> new RuntimeException("you are not a member of this group")) ;
        if(!admin.getRole().isHigherThan(GroupRoles.MODERATOR)) throw new RuntimeException("you don't have permission to delete a member from this group") ;
        if(!admin.getRole().isHigherThan(member.getRole())) throw new RuntimeException("you can't delete this member") ;

        switch (group.getStrategy()){
            case KEEP_FILES_ON_MEMBER_REMOVAL -> memberRepo.delete(member);
            case REMOVE_FILES_ON_MEMBER_REMOVAL -> {
                fileFeignController.deleteUserGroupFiles(username , groupId);
                memberRepo.delete(member);
            }
            case TRANSFER_FILES_TO_OWNER -> {
                fileFeignController.transferFilesToUser(username , groupId);
                memberRepo.delete(member);
            }
        }
    }

    public void removeAllUserMemberships () {
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(0 , 20) ;
        Page<Member> members = memberRepo.findByUsername(user , pageable) ;
        while (members.getTotalElements() > 0){
            memberRepo.deleteAll(members);
            members = memberRepo.findByGroupId(user , pageable) ;
        }

    }
}

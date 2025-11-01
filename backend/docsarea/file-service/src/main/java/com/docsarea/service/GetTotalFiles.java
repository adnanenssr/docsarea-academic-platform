package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTotalFiles {

    @Autowired
    FileRepo fileRepo ;
    @Autowired
    GroupFeignController groupFeignController ;

    public long userTotalFiles(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        return fileRepo.getTotalUserFiles(user) ;
    }

    public MemberDto isMember(String groupId , String username){
        return groupFeignController.getMember(groupId , username) ;
    }


    public long groupTotalFiles(String groupId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = isMember(groupId , user) ;

        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return fileRepo.getTotalGroupFiles(groupId) ;
        return fileRepo.getTotalMemberFiles(user , groupId) ;
    }

}

package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.controller.StorageFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFile {

    @Autowired
    FileRepo fileRepo ;
    @Autowired
    StorageFeignController storageFeignController ;
    @Autowired
    GroupFeignController groupFeignController ;

    public void userFileDelete (String fileId){
        Document document = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        if(document.getGroup() != null ) throw new RuntimeException("you can't delete this file , because it belongs to a group") ;
        boolean deleted = storageFeignController.deleteFile(document.getFilePath()) ;
        if(deleted) fileRepo.delete(document);
        else throw new RuntimeException("sorry we could not delete this file , please try again later") ;
    }

    public void groupFileDelete (String fileId , String groupId){
        Document document = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        if(document.getGroup() == null || !document.getGroup().equals(groupId) ) throw new RuntimeException("the file you are trying to delete does not belong to this group") ;
        MemberDto member = groupFeignController.getMember(groupId , CurrentAuthenticatedUser.getCurrentAuthenticatedUser()) ;
        if(!member.getRole().isHigherThan(GroupRoles.MODERATOR)) throw new RuntimeException("you don't have permission to delete a file from this group") ;
        boolean deleted = storageFeignController.deleteFile(document.getFilePath()) ;
        if(deleted) fileRepo.delete(document);
        else throw new RuntimeException("sorry we could not delete this file , please try again later") ;
    }

}

package com.docsarea.service;

import com.docsarea.enums.Status;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HandleUserDelete {
    @Autowired
    FileRepo fileRepo ;
    public boolean doesHaveGroupFiles(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        deleteUserFiles();
        return fileRepo.existsByOwnerAndGroupIdNotNullAndStatusIsNot(user , Status.REJECTED) ;

    }
    public void deleteUserFiles (){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(0 , 20) ;
        Page<Document> files = fileRepo.findByOwnerAndGroupIdIsNullOrderByModifiedAtDesc(username , pageable) ;
        while(files.getTotalElements() > 0) {
            fileRepo.deleteAll(files);
            files = fileRepo.findByOwnerAndGroupIdIsNullOrderByModifiedAtDesc(username , pageable) ;
        }
    }
}

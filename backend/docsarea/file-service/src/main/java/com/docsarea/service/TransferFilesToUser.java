package com.docsarea.service;

import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransferFilesToUser {
    @Autowired
    FileRepo fileRepo ;

    public void transferFilesToUser(String username , String groupId){
        Pageable pageable = PageRequest.of(0 , 20) ;
        Page<Document> files = fileRepo.findByOwnerAndGroupId(username , groupId , pageable) ;
        while(files.getTotalElements() > 0) {
            files.getContent().forEach(file -> file.setGroup(null));
            fileRepo.saveAll(files) ;
            files = fileRepo.findByOwnerAndGroupId(username , groupId , pageable) ;
        }
     }
}

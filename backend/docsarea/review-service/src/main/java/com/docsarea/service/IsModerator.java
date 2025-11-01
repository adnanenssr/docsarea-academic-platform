package com.docsarea.service;

import com.docsarea.repository.FileReviewRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IsModerator {
    @Autowired
    FileReviewRepo fileReviewRepo ;
    public boolean isModerator(String fileId ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        return fileReviewRepo.existsByFileIdAndModerator(fileId , user)  ;

    }
}

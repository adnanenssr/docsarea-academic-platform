package com.docsarea.service;

import com.docsarea.repository.UserRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUser {
    @Autowired
    UserRepo userRepo ;

    public void deleteThisUser(){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        userRepo.deleteByUsername(username);
    }
}

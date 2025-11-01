package com.docsarea.service;

import com.docsarea.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ExistsUsersService {

    @Autowired
    UserRepo userRepo ;

    public boolean isExistUsers(Set<String> users){
        return users.stream().allMatch(user -> userRepo.existsByUsername(user)) ;
    }
}

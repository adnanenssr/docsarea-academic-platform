package com.docsarea.service;

import com.docsarea.dtos.user.GetDto;
import com.docsarea.model.User;
import com.docsarea.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetSuggestions {
    @Autowired
    UserRepo userRepo ;
    public List<String> getSuggestions(String val){
        Pageable pageable = PageRequest.of(0 , 10) ;
        return  userRepo.findByUsernameStartingWith(val , pageable ).stream().map(User::getUsername).collect(Collectors.toList()) ;
    }
}

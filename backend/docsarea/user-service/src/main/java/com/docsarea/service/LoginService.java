package com.docsarea.service;

import com.docsarea.dtos.user.GetDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.User;
import com.docsarea.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.aot.generate.GeneratedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserRepo userRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;


    public GetDto getUser(String username){
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exists")) ;
        return modelMapper.map(user , GetDto.class) ;
    }
}

package com.docsarea.service;

import com.docsarea.dtos.user.RegisterDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterDtoMapper {

    ModelMapper modelMapper = DefaultMapper.getModelMapper()  ;
    @Autowired
    PasswordEncoder passwordEncoder ;

    public User userMapper(RegisterDto dto ){
        dto.setPassword(passwordEncoder.encode(dto.getPassword())) ;
        return modelMapper.map(dto , User.class) ;
    }

}

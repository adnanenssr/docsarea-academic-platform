package com.docsarea.service;

import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.RegisterDto;
import com.docsarea.exception.EmailAlreadyExistException;
import com.docsarea.exception.RegistrationFailedException;
import com.docsarea.exception.UsernameAlreadyExistException;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.User;
import com.docsarea.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RegisterService {
    @Autowired
    UserRepo userRepo ;
    @Autowired
    RegisterDtoMapper mapper ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;


    public void validUsername(String username){
        boolean isExist = userRepo.existsByUsername(username ) ;
        if(isExist) {
            throw new UsernameAlreadyExistException("username already exist") ;
        }
    }

    public void  validEmail(String email){
        boolean isExist = userRepo.existsByEmail(email) ;
        if(isExist) {
            throw new EmailAlreadyExistException("email already exist") ;
        }
    }



    public GetDto saveUser(RegisterDto registerDto){
        validUsername(registerDto.getUsername());
        validEmail(registerDto.getEmail());

        try {
            User user = mapper.userMapper(registerDto);
            User savedUser = userRepo.save(user);
            return modelMapper.map(savedUser, GetDto.class);
        }
        catch(Exception e ){
            throw new RegistrationFailedException("Unknown Error : Please Try Again Later ") ;
        }
    }


}

package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.controller.Test;
import com.docsarea.dtos.user.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    AuthFeignController authController ;
    @Autowired
    Test test ;

    public ResponseEntity<String> doRegister (RegisterDto dto ) {
        return authController.doRegister(dto) ;
    }

    public String test (){
        return test.test() ;
    }

}

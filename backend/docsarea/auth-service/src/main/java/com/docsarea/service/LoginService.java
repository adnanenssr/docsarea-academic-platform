package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.controller.KeycloakController;
import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.LoginDto;
import com.docsarea.dtos.user.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    KeycloakController keycloak ;
    @Autowired
    AuthFeignController authFeignController ;


    public AccessTokenDto sendLogin (LoginDto login){
        System.out.println(login.getPassword() + " " + login.getUsername());
        String form = "grant_type=password" + "&client_secret=S96qrVH9h46bLObgiFTWKQ4jtLtTGPj1" + "&client_id=docsarea-web-app" + "&username=" + login.getUsername() +"&password=" + login.getPassword() ;
        return keycloak.verifyLogin(form) ;

    }







}

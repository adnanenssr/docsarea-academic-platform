package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.controller.KeycloakController;
import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.PasswordDto;
import com.docsarea.dtos.user.RegisterDto;
import com.docsarea.dtos.user.UsernameEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {

    @Autowired
    KeycloakController keycloak ;
    @Autowired
    AuthFeignController authFeignController ;

    public String sendRegister(RegisterDto register){

        String form = "grant_type=client_credentials" + "&client_secret=S96qrVH9h46bLObgiFTWKQ4jtLtTGPj1" + "&client_id=docsarea-web-app" ;

        UsernameEmailDto usernameEmailDto = new UsernameEmailDto();
        usernameEmailDto.setUsername(register.getUsername());
        usernameEmailDto.setEmail(register.getEmail());
        usernameEmailDto.setEnabled(true);
        PasswordDto passwordDto = new PasswordDto() ;
        passwordDto.setValue(register.getPassword());

        AccessTokenDto accessToken = keycloak.getAccessToken(form) ;


        String bearer = "bearer ".concat(accessToken.getAccessToken()) ;
        String url = keycloak.saveUser(bearer , usernameEmailDto ).headers().get("Location").toString();
        String id = url.substring(url.lastIndexOf("/" ) + 1 , url.lastIndexOf("]")) ;

        keycloak.addPassword(bearer , id , passwordDto );

        return "successfully registered " ;


    }

    public GetDto RegisterDb (RegisterDto dto){
        return authFeignController.savedb(dto) ;
    }

}

package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.controller.KeycloakController;
import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.user.*;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    @Autowired
    KeycloakController keycloak ;
    @Autowired
    AuthFeignController authFeignController ;
    @Autowired
    LoginService loginService ;


    public String getAccessToken (){
        String form = "grant_type=client_credentials" + "&client_secret=S96qrVH9h46bLObgiFTWKQ4jtLtTGPj1" + "&client_id=docsarea-web-app" ;

        AccessTokenDto accessToken = keycloak.getAccessToken(form) ;
        return accessToken.getAccessToken() ;

    }

    public GetDto update (EmailDto dto , String userId){

        String token = getAccessToken() ;

        keycloak.update("bearer " + token , userId , dto );
        return authFeignController.updatedb(dto) ;
    }

    public GetDto changePassword (ChangePasswordDto dto , String userId ){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        LoginDto login = new LoginDto() ;
        login.setPassword(dto.getOldPassword());
        login.setUsername(username);

        try {
            loginService.sendLogin(login) ;
        }
        catch (Exception e){
            throw new RuntimeException("Username or Password is incorrect ") ;
        }
        PasswordDto passwordDto = new PasswordDto() ;
        passwordDto.setValue(dto.getNewPassword());

        String bearer = "bearer ".concat(getAccessToken()) ;

        try{
            keycloak.addPassword(bearer , userId , passwordDto );
        }catch (Exception e){
            throw new RuntimeException("could not change your password ! please try again later") ;
        }


        return authFeignController.changePassword(dto.getNewPassword()) ;
    }



}

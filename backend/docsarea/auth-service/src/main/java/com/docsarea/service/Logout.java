package com.docsarea.service;

import com.docsarea.controller.KeycloakController;
import com.docsarea.dtos.keycloak.AccessTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Logout {

    @Autowired
    KeycloakController keycloak ;


    public String getAccessToken (){
        String form = "grant_type=client_credentials" + "&client_secret=S96qrVH9h46bLObgiFTWKQ4jtLtTGPj1" + "&client_id=docsarea-web-app" ;

        AccessTokenDto accessToken = keycloak.getAccessToken(form) ;
        return accessToken.getAccessToken() ;

    }

    public void logout (String userId){

        String token = getAccessToken() ;
        keycloak.logout("bearer " + token , userId);

    }
}

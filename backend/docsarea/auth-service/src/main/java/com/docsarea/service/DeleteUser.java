package com.docsarea.service;

import com.docsarea.controller.*;
import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.keycloak.DisableUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUser {
    @Autowired
    KeycloakController keycloak ;
    @Autowired
    FileFeignController fileFeignController ;
    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    AuthFeignController authFeignController ;
    @Autowired
    GatewayFeignController gatewayFeignController ;

    public String getAccessToken (){
        String form = "grant_type=client_credentials" + "&client_secret=S96qrVH9h46bLObgiFTWKQ4jtLtTGPj1" + "&client_id=docsarea-web-app" ;
        AccessTokenDto accessToken = keycloak.getAccessToken(form) ;
        return accessToken.getAccessToken() ;
    }

    public void deleteUser (String userId){
        String token = getAccessToken() ;
        keycloak.delete( "bearer " + token ,userId);
        authFeignController.deleteUser();
    }

    public void disableUser (String userId){
        DisableUserDto disableUser = new DisableUserDto() ;
        String accessToken = getAccessToken() ;
        keycloak.disable("bearer " + accessToken , userId , disableUser);
    }

    public void handleDelete(String userId){
        boolean doesHaveGroupFiles = fileFeignController.haveGroupFiles();
        if(doesHaveGroupFiles) disableUser(userId);
        else deleteUser(userId);
        groupFeignController.removeAllUserMemberships();
        groupFeignController.deleteAllUserGroups();
        gatewayFeignController.logout();
    }



}

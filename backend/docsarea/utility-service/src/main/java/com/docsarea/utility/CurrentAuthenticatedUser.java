package com.docsarea.utility;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


public class CurrentAuthenticatedUser {

    public static String getCurrentAuthenticatedUser(){
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("preferred_username") ;
    }
}

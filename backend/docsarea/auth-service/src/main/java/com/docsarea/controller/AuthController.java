package com.docsarea.controller;

import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.user.*;
import com.docsarea.service.*;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    RegisterService registerService;
    @Autowired
    LoginService loginService;
    @Autowired
    UpdateService updateService;
    @Autowired
    DeleteUser deleteUser ;
    @Autowired
    Logout logout ;

    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        registerService.RegisterDb(dto);
        String s = registerService.sendRegister(dto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/auth/login")
    public String login(@RequestBody LoginDto dto) {

        return loginService.sendLogin(dto).getAccessToken();

    }

    @PutMapping(value = "/auth/update")
    public GetDto update(@RequestBody EmailDto dto) {
        String userId =((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("sub") ;
        return updateService.update(dto, userId);
    }

    @PutMapping(value = "/auth/change/password")
    public GetDto updatePassword(@RequestBody ChangePasswordDto dto) {
        String userId =((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("sub") ;
        return updateService.changePassword(dto , userId);
    }

    @DeleteMapping(value = "/auth/delete")
    public void handleDelete () {
        String userId = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("sub");
        deleteUser.handleDelete(userId);
    }
    @PostMapping(value = "/auth/logout")
    public void logout (){
        String userId = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("sub");
        logout.logout(userId);
    }
}
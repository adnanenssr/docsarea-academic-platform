package com.docsarea.controller;

import com.docsarea.dtos.user.*;
import com.docsarea.service.LoginService;
import com.docsarea.service.Logout;
import com.docsarea.service.RegisterService;
import com.docsarea.service.UpdateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    RegisterService registerService ;
    @Autowired
    LoginService loginService ;
    @Autowired
    UpdateService updateService ;
    @Autowired
    AuthFeignController authFeignController ;
    @Autowired
    Logout logout ;

    @PostMapping(value = "/register" , consumes = "Application/json")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto ){
        return registerService.doRegister(dto) ;
    }

    @PostMapping(value = "/login")
    public String login (@RequestBody LoginDto dto , HttpServletResponse response){
        String token = loginService.doLogin(dto) ;
        loginService.createCookie(token , response);
        return token ;
    }

    @GetMapping(value = "/test")
    public String test (Authentication auth){
        return ((Jwt) auth.getPrincipal()).getClaimAsString("sub") ;
    }

    @PutMapping(value = "/user/update")
    public GetDto update(@RequestBody EmailDto dto) {
        return updateService.update(dto) ;
    }

    @PutMapping(value = "/auth/change/password")
    public GetDto updatePassword(@RequestBody ChangePasswordDto dto) {
        return authFeignController.updatePassword(dto);
    }
    @DeleteMapping(value = "/auth/delete")
    public void handleDelete () {
        authFeignController.handleDelete();
    }
    @PostMapping(value = "/auth/logout")
    public void logout (HttpServletResponse response){
        logout.invalidateCookie(response);
        logout.doLogout();

    }







}

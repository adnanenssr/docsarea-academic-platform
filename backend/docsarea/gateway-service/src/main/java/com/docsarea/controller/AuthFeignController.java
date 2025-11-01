package com.docsarea.controller;

import com.docsarea.dtos.user.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@FeignClient("auth-service")
public interface AuthFeignController {
    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> doRegister(@RequestBody RegisterDto dto) ;

    @PostMapping(value = "/auth/login")
    public String doLogin(@RequestBody LoginDto dto ) ;

    @PutMapping(value = "/auth/update")
    public GetDto doUpdate (@RequestBody EmailDto dto ) ;

    @PutMapping(value = "/auth/change/password")
    public GetDto updatePassword(@RequestBody ChangePasswordDto dto) ;

    @DeleteMapping(value = "/auth/delete")
    public void handleDelete () ;
    @PostMapping(value = "/auth/logout")
    public void logout () ;





}


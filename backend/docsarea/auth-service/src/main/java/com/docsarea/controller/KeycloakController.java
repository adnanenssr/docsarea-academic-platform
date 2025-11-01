package com.docsarea.controller;

import com.docsarea.dtos.keycloak.AccessTokenDto;
import com.docsarea.dtos.keycloak.DisableUserDto;
import com.docsarea.dtos.user.EmailDto;
import com.docsarea.dtos.user.PasswordDto;
import com.docsarea.dtos.user.UsernameEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "keycloak", url = "http://localhost:8080")
public interface KeycloakController {

    @PostMapping(value = "/realms/Docsarea/protocol/openid-connect/token" ,  consumes = "application/x-www-form-urlencoded")
     AccessTokenDto getAccessToken(
            @RequestBody String form )  ;

    @PostMapping (value = "/admin/realms/Docsarea/users")
     feign.Response saveUser(@RequestHeader("Authorization") String bearerToken ,
                             @RequestBody UsernameEmailDto dto ) ;

    @PutMapping(value = "/admin/realms/Docsarea/users/{id}/reset-password")
    void addPassword (@RequestHeader("Authorization") String bearerToken ,
                      @PathVariable String id ,
                      @RequestBody PasswordDto dto) ;

    @PostMapping(value = "/realms/Docsarea/protocol/openid-connect/token" , consumes = "application/x-www-form-urlencoded")
    AccessTokenDto verifyLogin (@RequestBody String form);

    @PutMapping(value = "/admin/realms/Docsarea/users/{id}")
    void update (@RequestHeader("Authorization") String bearer ,
                 @PathVariable String id ,
                 @RequestBody EmailDto dto ) ;

    @PutMapping(value = "/admin/realms/Docsarea/users/{id}" , consumes = "application/json")
    void disable (@RequestHeader("Authorization") String bearer ,
                  @PathVariable String id ,
                  @RequestBody DisableUserDto disable) ;

    @DeleteMapping(value = "/admin/realms/Docsarea/users/{id}")
    void delete (@RequestHeader("Authorization") String bearer ,
                 @PathVariable String id) ;

    @PostMapping(value = "/admin/realms/Docsarea/users/{id}/logout")
    void logout (@RequestHeader("Authorization") String bearer ,
                 @PathVariable String id) ;




}

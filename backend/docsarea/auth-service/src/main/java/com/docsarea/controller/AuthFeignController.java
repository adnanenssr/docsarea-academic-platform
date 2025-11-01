package com.docsarea.controller;

import com.docsarea.dtos.user.EmailDto;
import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.RegisterDto;
import com.docsarea.dtos.user.UsernameEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(  name = "user-service")
public interface AuthFeignController {

    @PostMapping(value = "/api/register")
    public GetDto savedb(@RequestBody RegisterDto dto);

    @PutMapping (value = "/api/update")
    public GetDto updatedb(@RequestBody EmailDto dto) ;

    @PutMapping(value = "/api/update/password")
    public GetDto changePassword (@RequestBody String dto);


    @DeleteMapping(value = "/user/delete")
    public void deleteUser ();
}

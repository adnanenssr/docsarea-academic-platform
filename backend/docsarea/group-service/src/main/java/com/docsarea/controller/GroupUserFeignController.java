package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(name = "user-service")
public interface GroupUserFeignController {
    @PostMapping(value = "/api/exist")
    public boolean isExist(@RequestBody Set<String> username) ;
}

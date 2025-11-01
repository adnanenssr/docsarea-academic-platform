package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient("user-service")
public interface UserFeignController {

    @PostMapping(value = "/api/exist")
    public boolean isExistUsers(@RequestBody Set<String> users) ;
}

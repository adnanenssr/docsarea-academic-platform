package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface Test {

    @GetMapping("/test")
    public String test () ;
}

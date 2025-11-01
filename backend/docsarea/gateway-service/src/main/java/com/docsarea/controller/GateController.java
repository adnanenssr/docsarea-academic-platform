package com.docsarea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GateController {
    @GetMapping(value = "/authenticated")
    public HttpStatus isAuthenticated(){
        return HttpStatus.OK ;
    }


}

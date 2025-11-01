package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("gateway-service")
public interface GatewayFeignController {
    @PostMapping(value = "/auth/logout")
    public void logout ();

}

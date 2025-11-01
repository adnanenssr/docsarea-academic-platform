package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("file-service")
public interface FileFeignController {
    @GetMapping(value = "/have/group/file")
    public boolean haveGroupFiles();

}

package com.docsarea.controller;

import com.docsarea.enums.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("file-service")
public interface FileFeignController {
    @PutMapping(value = "/update/file/status/{fileId}")
    public void updateStatus (@PathVariable String fileId , @RequestParam("status") Status status );

}

package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient("storage-service")
public interface StorageFeignController {
    @PutMapping(value = "/storage/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveAvatar (@RequestPart("file") MultipartFile file) throws IOException;

    @PutMapping(value = "/storage/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveCover (@RequestPart("file") MultipartFile file) throws IOException;

}

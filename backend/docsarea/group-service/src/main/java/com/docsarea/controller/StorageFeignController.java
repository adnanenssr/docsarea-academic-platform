package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@FeignClient("storage-service")
public interface StorageFeignController {
    @PutMapping(value = "/storage/save/avatar/{groupId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveGroupAvatar (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException;

    @PutMapping(value = "/storage/save/cover/{groupId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveGroupCover (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException ;

}

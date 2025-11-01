package com.docsarea.controller;

import com.docsarea.dtos.file.StorageFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("storage-service")
public interface StorageFeignController {
    @PostMapping(value = "/storage/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StorageFileDto upload (@RequestParam("fileId") String id , @RequestPart("file") MultipartFile file) ;

    @PutMapping(value = "/storage/update/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StorageFileDto update (@RequestPart("file") MultipartFile file , @PathVariable String id) ;

    @GetMapping(value = "/storage/file/{id}")
    public Resource getFile(@PathVariable String id , @RequestParam("path") String path) ;

    @DeleteMapping(value = "/storage/delete")
    public boolean deleteFile (@RequestParam("path") String path);

}

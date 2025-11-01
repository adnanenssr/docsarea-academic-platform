package com.docsarea.controller;

import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.service.*;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class StorageController {
    @Autowired
    FileUpload fileUpload ;
    @Autowired
    GetFileService getFileService ;
    @Autowired
    SaveAvatar saveAvatar ;
    @Autowired
    SaveCover saveCover ;
    @Autowired
    DeleteFile deleteFile ;



    @PostMapping(value = "/storage/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "application/json")
    public StorageFileDto upload(@RequestPart("file")MultipartFile file, @RequestParam("fileId") String fileId) throws IOException {
        return fileUpload.saveFile(file , fileId) ;
    }

    @PutMapping(value = "/storage/update/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StorageFileDto update (@RequestPart("file") MultipartFile file , @PathVariable String id ) throws IOException {
        return fileUpload.saveFile(file , id);
    }

    @GetMapping(value = "/storage/file/{id}")
    public Resource getFile(@PathVariable String id , @RequestParam("path") String path){
        return getFileService.getFile(id , path) ;
    }

    @PutMapping(value = "/storage/save/avatar")
    public String saveAvatar (@RequestPart("file") MultipartFile file) throws IOException {
        return saveAvatar.saveAvatar(file);
    }

    @PutMapping(value = "/storage/save/cover")
    public String saveCover (@RequestPart("file") MultipartFile file) throws IOException {
        return saveCover.saveCover(file);
    }

    @PutMapping(value = "/storage/save/avatar/{groupId}")
    public String saveGroupAvatar (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException {
        return saveAvatar.saveGroupAvatar(file , groupId);
    }

    @PutMapping(value = "/storage/save/cover/{groupId}")
    public String saveGroupCover (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException {
        return saveCover.saveGroupCover(file , groupId);
    }
    @DeleteMapping(value = "/storage/delete")
    public boolean deleteFile (@RequestParam("path") String path){
        return deleteFile.deleteFile(path) ;
    }
}

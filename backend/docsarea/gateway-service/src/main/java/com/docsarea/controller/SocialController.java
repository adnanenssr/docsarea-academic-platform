package com.docsarea.controller;

import com.docsarea.dtos.social.BookmarkDto;
import com.docsarea.dtos.social.DownloadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialController {

    @Autowired
    SocialFeignController socialFeignController ;

    @PostMapping(value = "/add/bookmark/{fileId}")
    public void addBookmark (@PathVariable String fileId){
        socialFeignController.addBookmark(fileId);
    }
    @DeleteMapping(value = "/remove/bookmark/{fileId}")
    public void deleteBookmark(@PathVariable String fileId){
        socialFeignController.deleteBookmark(fileId);
    }
    @GetMapping(value = "/get/exist/bookmark/{fileId}")
    public boolean existsBookmark(@PathVariable String fileId){
        return socialFeignController.existsBookmark(fileId) ;
    }







}

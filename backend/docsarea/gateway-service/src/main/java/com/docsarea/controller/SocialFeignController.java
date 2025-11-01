package com.docsarea.controller;

import com.docsarea.dtos.social.BookmarkDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("social-service")
public interface SocialFeignController {

    @PostMapping(value = "/add/bookmark/{fileId}")
    public void addBookmark (@PathVariable String fileId);

    @DeleteMapping(value = "/remove/bookmark/{fileId}")
    public void deleteBookmark(@PathVariable String fileId) ;

    @GetMapping(value = "/get/exist/bookmark/{fileId}")
    public boolean existsBookmark(@PathVariable String fileId);







}

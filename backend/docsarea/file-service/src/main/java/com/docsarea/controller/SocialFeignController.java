package com.docsarea.controller;

import com.docsarea.dtos.social.BookmarkDto;
import com.docsarea.dtos.social.DownloadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("social-service")
public interface SocialFeignController {

    @PostMapping(value = "/add/download/{fileId}")
    public void addDownload (@PathVariable String fileId);
    @GetMapping(value = "/get/downloads")
    public DownloadDto getDownloads(@RequestParam("page") int page) ;
    @PostMapping(value = "/add/bookmark/{fileId}")
    public void addBookmark (@PathVariable String fileId);
    @GetMapping(value = "/get/bookmarks")
    public BookmarkDto getBookmarks(@RequestParam("page") int page );

}

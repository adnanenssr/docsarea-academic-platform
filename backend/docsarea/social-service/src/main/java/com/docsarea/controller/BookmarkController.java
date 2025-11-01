package com.docsarea.controller;

import com.docsarea.dtos.social.BookmarkDto;
import com.docsarea.service.CrudBookmark;
import com.docsarea.service.GetBookmarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookmarkController {

    @Autowired
    CrudBookmark crudBookmark;

    @Autowired
    GetBookmarks getBookmarks ;

    @PostMapping(value = "/add/bookmark/{fileId}")
    public void addBookmark (@PathVariable String fileId){
        crudBookmark.addBookmark(fileId);
    }

    @GetMapping(value = "/get/bookmarks")
    public BookmarkDto getBookmarks(@RequestParam("page") int page ){
        return getBookmarks.getBookmarks(page) ;
    }

    @DeleteMapping(value = "/remove/bookmark/{fileId}")
        public void deleteBookmark(@PathVariable String fileId){
        crudBookmark.removeBookmark(fileId);
    }
    @GetMapping(value = "/get/exist/bookmark/{fileId}")
    public boolean existsBookmark(@PathVariable String fileId){
        return crudBookmark.existsBookmark(fileId) ;
    }
}

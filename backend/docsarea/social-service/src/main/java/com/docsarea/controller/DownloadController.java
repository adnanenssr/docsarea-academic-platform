package com.docsarea.controller;

import com.docsarea.dtos.social.DownloadDto;
import com.docsarea.service.AddDownload;
import com.docsarea.service.GetDownloads;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DownloadController {

    @Autowired
    AddDownload addDownload ;
    @Autowired
    GetDownloads getDownloads ;

    @PostMapping(value = "/add/download/{fileId}")
    public void addDownload (@PathVariable String fileId){
        addDownload.addDownload(fileId);
    }

    @GetMapping(value = "/get/downloads")
    public DownloadDto getDownloads(@RequestParam("page") int page) {
        return getDownloads.getDownloads(page) ;
    }
}

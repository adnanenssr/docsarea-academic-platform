package com.docsarea.controller;

import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class DownloadsController {
    @Autowired
    AddDownload addDownload ;
    @Autowired
    GetDownloads getDownloads ;
    @Autowired
    GetTotalStats getTotalStats ;

    @PutMapping(value = "/download/add/{fileId}/{publisher}/{owner}")
    public void addDownload (@PathVariable String fileId , @PathVariable String publisher , @PathVariable String owner){
        addDownload.addDownload(fileId , publisher , owner);
    }

    @GetMapping(value = "/get/downloads/user")
    public Map<LocalDate, Long > getUserDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    ){
        return getDownloads.getUserDownloadsByWeek(start , end) ;
    }
    @GetMapping(value = "/get/downloads/group/{publisher}")
    public Map<LocalDate, Long > getGroupDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    ){
        return getDownloads.groupDownloadsByWeek(publisher ,start , end) ;
    }



    @GetMapping(value = "/get/downloads/files/{publisher}")
    public DownloadsPageDto getTotalDownloadsPerFile(@RequestParam("start") LocalDate start ,
                                             @RequestParam("end") LocalDate end ,
                                             @RequestParam("page") int page ,
                                             @PathVariable String publisher
    ){
        return getDownloads.getTotalDownloadsByFile(publisher , start , end , page) ;
    }

    @GetMapping(value = "/get/downloads/files/member/{owner}/{publisher}")
    public DownloadsPageDto getMemberTotalDownloadsPerFile(@PathVariable String publisher ,
                                                           @PathVariable String owner ,
                                                           @RequestParam("start") LocalDate start ,
                                                           @RequestParam("end") LocalDate end ,
                                                           @RequestParam("page") int page
    ) {
        return getDownloads.getMemberTotalDownloadsByFile(publisher , owner ,start, end, page);
    }

}

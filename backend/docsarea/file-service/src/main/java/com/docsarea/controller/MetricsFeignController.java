package com.docsarea.controller;

import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.dtos.views.ViewsPageDto;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FeignClient(name = "metrics-service")
public interface MetricsFeignController {
    @GetMapping(value = "/get/views/files/{publisher}")
    public ViewsPageDto getTotalViewsPerFile(@PathVariable String publisher ,
                                             @RequestParam("start") LocalDate start ,
                                             @RequestParam("end") LocalDate end ,
                                             @RequestParam("page") int page
    ) ;

    @PutMapping(value = "/view/add/{fileId}/{publisher}/{owner}")
    public void addView (@PathVariable String fileId , @PathVariable String publisher , @PathVariable String owner) ;

    @GetMapping(value = "/get/downloads/files/{publisher}")
    public DownloadsPageDto getTotalDownloadsPerFile(@PathVariable String publisher ,
                                                             @RequestParam("start") LocalDate start ,
                                                             @RequestParam("end") LocalDate end ,
                                                             @RequestParam("page") int page
    ) ;

    @PutMapping(value = "/download/add/{fileId}/{publisher}/{owner}")
    public void addDownload (@PathVariable String fileId , @PathVariable String publisher , @PathVariable String owner) ;
    @GetMapping(value = "/get/downloads/files/member/{publisher}/{owner}")
    public DownloadsPageDto getMemberTotalDownloadsPerFile(@PathVariable String publisher ,
                                                     @PathVariable String owner ,
                                                     @RequestParam("start") LocalDate start ,
                                                     @RequestParam("end") LocalDate end ,
                                                     @RequestParam("page") int page
    ) ;
    @GetMapping(value = "/get/views/files/member/{owner}/{publisher}")
    public ViewsPageDto getMemberTotalViewsPerFile(@PathVariable String publisher ,
                                             @PathVariable String owner ,
                                             @RequestParam("start") LocalDate start ,
                                             @RequestParam("end") LocalDate end ,
                                             @RequestParam("page") int page
    ) ;





}

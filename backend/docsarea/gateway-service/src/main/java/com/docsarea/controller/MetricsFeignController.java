package com.docsarea.controller;

import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.dtos.social.BookmarkDto;
import com.docsarea.dtos.views.ViewsPageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@FeignClient("metrics-service")
public interface MetricsFeignController {
    @GetMapping(value = "/get/views/user")
    public Map<LocalDate, Long > getUserViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    );
    @GetMapping(value = "/get/views/group/{publisher}")
    public Map<LocalDate, Long > getGroupViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    );
    @GetMapping(value = "/get/views/member/{publisher}")
    public Map<LocalDate, Long > getMemberViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    );



    @GetMapping(value = "/get/views/files/{publisher}")
    public ViewsPageDto getTotalViewsPerFile(@RequestParam("start") LocalDate start ,
                                             @RequestParam("end") LocalDate end ,
                                             @RequestParam("page") int page ,
                                             @PathVariable String publisher
    );

    @GetMapping(value = "/get/views/files/member/{publisher}")
    public ViewsPageDto getMemberTotalViewsPerFile(@RequestParam("start") LocalDate start ,
                                                   @RequestParam("end") LocalDate end ,
                                                   @RequestParam("page") int page ,
                                                   @PathVariable String publisher
    );
    @GetMapping(value = "/get/total/group/{publisher}")
    public List<String> getGroupTotalMetrics(@PathVariable  String publisher);
    @GetMapping(value = "/get/total/user")
    public List<String> getUserTotalMetrics();
    @GetMapping(value = "/get/total/member/{publisher}")
    public List<String> getMemberTotalMetrics(@PathVariable  String publisher);
    @GetMapping(value = "/get/downloads/user")
    public Map<LocalDate, Long > getUserDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    );
    @GetMapping(value = "/get/downloads/group/{publisher}")
    public Map<LocalDate, Long > getGroupDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    );
    @GetMapping(value = "/get/downloads/member/{publisher}")
    public Map<LocalDate, Long > getMemberDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    );



    @GetMapping(value = "/get/downloads/files/{publisher}")
    public DownloadsPageDto getTotalDownloadsPerFile(@RequestParam("start") LocalDate start ,
                                                     @RequestParam("end") LocalDate end ,
                                                     @RequestParam("page") int page ,
                                                     @PathVariable String publisher
    );

    @GetMapping(value = "/get/downloads/files/member/{publisher}")
    public DownloadsPageDto getMemberTotalDownloadsPerFile(@RequestParam("start") LocalDate start ,
                                                           @RequestParam("end") LocalDate end ,
                                                           @RequestParam("page") int page ,
                                                           @PathVariable String publisher
    ) ;

    @PostMapping(value = "/add/bookmark/{fileId}")
    public void addBookmark (@PathVariable String fileId);


}

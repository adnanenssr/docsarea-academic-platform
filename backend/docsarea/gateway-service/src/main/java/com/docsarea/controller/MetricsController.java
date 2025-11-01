package com.docsarea.controller;

import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.dtos.views.ViewsPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class MetricsController {
    @Autowired
    MetricsFeignController metricsFeignController ;

    @GetMapping(value = "/get/views/user")
    public Map<LocalDate, Long > getUserViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    ){
        return metricsFeignController.getUserViewsByWeek( start , end ) ;
    }
    @GetMapping(value = "/get/views/group/{publisher}")
    public Map<LocalDate, Long > getGroupViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    ){
        return metricsFeignController.getGroupViewsByWeek( start , end , publisher) ;
    }


    @GetMapping(value = "/get/total/group/{publisher}")
    public List<String> getGroupTotalMetrics(@PathVariable String publisher){
        return metricsFeignController.getGroupTotalMetrics(publisher) ;
    }
    @GetMapping(value = "/get/total/user")
    public List<String> getUserTotalMetrics(){
        return metricsFeignController.getUserTotalMetrics();
    }
    @GetMapping(value = "/get/downloads/user")
    public Map<LocalDate, Long > getUserDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    ){
        return metricsFeignController.getUserDownloadsByWeek( start , end ) ;
    }
    @GetMapping(value = "/get/downloads/group/{publisher}")
    public Map<LocalDate, Long > getGroupDownloadsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    ){
        return metricsFeignController.getGroupDownloadsByWeek( start , end , publisher) ;
    }








}

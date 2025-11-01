package com.docsarea.controller;

import com.docsarea.dtos.views.ViewsPageDto;
import com.docsarea.dtos.views.ViewsPageDto;
import com.docsarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class ViewsController {
    @Autowired
    AddView addView ;
    @Autowired
    GetViews getViews ;
    @Autowired
    GetTotalStats getTotalStats ;

    @PutMapping(value = "/view/add/{fileId}/{publisher}/{owner}")
    public void addView (@PathVariable String fileId , @PathVariable String publisher , @PathVariable String owner){
        addView.addView(fileId , publisher , owner);
    }

    @GetMapping(value = "/get/views/user")
    public Map<LocalDate, Long > getUserViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end
    ){
        return getViews.getUserViewsByWeek(start , end) ;
    }
    @GetMapping(value = "/get/views/group/{publisher}")
    public Map<LocalDate, Long > getGroupViewsByWeek(
            @RequestParam("start") LocalDate start ,
            @RequestParam("end") LocalDate end ,
            @PathVariable String publisher
    ){
        return getViews.groupViewsByWeek(publisher ,start , end) ;
    }



    @GetMapping(value = "/get/views/files/{publisher}")
    public ViewsPageDto getTotalViewsPerFile(@RequestParam("start") LocalDate start ,
                                                     @RequestParam("end") LocalDate end ,
                                                     @RequestParam("page") int page ,
                                                     @PathVariable String publisher
    ){
        return getViews.getTotalViewsByFile(publisher , start , end , page) ;
    }

    @GetMapping(value = "/get/views/files/member/{owner}/{publisher}")
    public ViewsPageDto getMemberTotalViewsPerFile(@PathVariable String publisher ,
                                                           @PathVariable String owner ,
                                                           @RequestParam("start") LocalDate start ,
                                                           @RequestParam("end") LocalDate end ,
                                                           @RequestParam("page") int page
    ) {
        return getViews.getMemberTotalViewsByFile(publisher , owner ,start, end, page);
    }
    @GetMapping(value = "/get/total/group/{publisher}")
    public List<String> getGroupTotalMetrics(@PathVariable  String publisher){
        return getTotalStats.groupTotalMetrics(publisher) ;
    }
    @GetMapping(value = "/get/total/user")
    public List<String> getUserTotalMetrics(){
        return getTotalStats.userTotalMetrics();
    }




}

package com.docsarea.controller;

import com.docsarea.dtos.review.CreateReviewDto;
import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.ReviewPageDto;
import com.docsarea.dtos.review.UpdateReview;
import com.docsarea.enums.Status;
import com.docsarea.service.AddFileReviewService;
import com.docsarea.service.FileReviewService;
import com.docsarea.service.GetReviewFiles;
import com.docsarea.service.IsModerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
public class FileReviewController {
    @Autowired
    AddFileReviewService addFileReviewService ;
    @Autowired
    FileReviewService fileReviewService ;
    @Autowired
    GetReviewFiles getReviewFiles;
    @Autowired
    IsModerator isModerator ;


    @PostMapping(value = "/review/file")
    public GetFileReview addFileReview(@RequestBody CreateReviewDto dto){
        return addFileReviewService.createReview(dto) ;
    }
    @PutMapping(value = "/{groupId}/review/{id}")
    public GetFileReview reviewFile(@RequestBody UpdateReview dto ,@PathVariable String groupId , @PathVariable String id){
        return fileReviewService.updateReview(dto , groupId , id) ;
    }

    @GetMapping(value = "/get/review/moderator/{groupId}")
    public ReviewPageDto getModeratorReviews(@PathVariable String groupId  , @RequestParam("status") Status status , @RequestParam("page") int page ) {
        return getReviewFiles.getModeratorReviews(groupId , status , page) ;
    }

    @GetMapping(value = "/get/review/group/{groupId}")
    public ReviewPageDto getGroupReviews( @PathVariable String groupId  , @RequestParam("status") Status status , @RequestParam("page") int page ) {
        return getReviewFiles.getGroupReviews(groupId , status , page) ;
    }

    @GetMapping(value = "/is/moderator/{fileId}")
    public boolean isModerator(@PathVariable String fileId) {
        return isModerator.isModerator(fileId) ;
    }

    @GetMapping(value = "/review/get/{groupId}/{fileId}")
    public GetFileReview getFile (@PathVariable String groupId , @PathVariable String fileId){
       return  getReviewFiles.getFileReview(groupId , fileId) ;
    }

}

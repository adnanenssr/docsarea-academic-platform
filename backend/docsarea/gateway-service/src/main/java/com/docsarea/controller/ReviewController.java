package com.docsarea.controller;

import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.UpdateReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    ReviewFeignController reviewFeignController ;

    @PutMapping(value = "/{groupId}/review/{id}")
    public GetFileReview reviewFile(@RequestBody UpdateReview dto , @PathVariable String groupId , @PathVariable String id){
        return reviewFeignController.reviewFile(dto , groupId , id) ;
    }
    @GetMapping(value = "/review/get/{groupId}/{fileId}")
    public GetFileReview getFile (@PathVariable String groupId , @PathVariable String fileId){
        return  reviewFeignController.getFile(groupId , fileId) ;
    }

}

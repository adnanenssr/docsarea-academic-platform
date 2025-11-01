package com.docsarea.controller;

import com.docsarea.dtos.review.CreateReviewDto;
import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.ReviewPageDto;
import com.docsarea.enums.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("review-service")
public interface ReviewFeignController {
    @PostMapping(value = "/review/file")
    public GetFileReview sendReview(@RequestBody CreateReviewDto dto);
    @GetMapping(value = "/get/review/moderator/{groupId}")
    public ReviewPageDto getModeratorReviews(@PathVariable String groupId  , @RequestParam("status") Status status , @RequestParam("page") int page ) ;


    @GetMapping(value = "/get/review/group/{groupId}")
    public ReviewPageDto getGroupReviews(@PathVariable String groupId  , @RequestParam("status") Status status , @RequestParam("page") int page ) ;

    @GetMapping(value = "/is/moderator/{fileId}")
    public boolean isModerator(@PathVariable String fileId) ;


    }

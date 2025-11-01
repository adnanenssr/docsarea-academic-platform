package com.docsarea.controller;

import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.UpdateReview;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("review-service")
public interface ReviewFeignController {
    @PutMapping(value = "/{groupId}/review/{id}")
    public GetFileReview reviewFile(@RequestBody UpdateReview dto , @PathVariable String groupId , @PathVariable String id) ;
    @GetMapping(value = "/review/get/{groupId}/{fileId}")
    public GetFileReview getFile (@PathVariable String groupId , @PathVariable String fileId);

}

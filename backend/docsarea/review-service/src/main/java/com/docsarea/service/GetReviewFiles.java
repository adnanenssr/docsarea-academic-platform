package com.docsarea.service;

import com.docsarea.dtos.review.FileIdModeratorDto;
import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.ReviewPageDto;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.FileReview;
import com.docsarea.repository.FileReviewRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetReviewFiles {
    @Autowired
    FileReviewRepo fileReviewRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;


    public ReviewPageDto getModeratorReviews (String groupId , Status status , int page ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<FileReview> reviews = fileReviewRepo.findByModeratorAndGroupIdAndStatus(user , groupId , status , pageable) ;
        Set<FileIdModeratorDto> files = reviews.stream().map((file) -> {
            FileIdModeratorDto dto = new FileIdModeratorDto() ;
            dto.setModerator(file.getModerator());
            dto.setFileId(file.getFileId());
            return dto ;
        }).collect(Collectors.toSet());
        ReviewPageDto reviewsPage = new ReviewPageDto() ;
        reviewsPage.setReviews(files);
        reviewsPage.setNumPages(reviews.getTotalPages());
        reviewsPage.setNumElements(reviews.getTotalElements());

        return reviewsPage ;
    }

    public ReviewPageDto getGroupReviews (String groupId , Status status , int page ){
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<FileReview> reviews = fileReviewRepo.findByGroupIdAndStatus( groupId , status , pageable) ;
        Set<FileIdModeratorDto> files = reviews.stream().map((file) -> modelMapper.map(file , FileIdModeratorDto.class)).collect(Collectors.toSet());
        ReviewPageDto reviewsPage = new ReviewPageDto() ;
        reviewsPage.setReviews(files);
        reviewsPage.setNumPages(reviews.getTotalPages());
        reviewsPage.setNumElements(reviews.getTotalElements());
        return reviewsPage ;
    }

    public GetFileReview getFileReview (String groupId , String fileId ){
        FileReview file = fileReviewRepo.findByGroupIdAndFileId(groupId , fileId).orElseThrow(() -> new RuntimeException("this file review does not exist ")) ;
        return modelMapper.map(file , GetFileReview.class) ;
    }

}

package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.review.CreateReviewDto;
import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.enums.GroupRoles;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.FileReview;
import com.docsarea.repository.FileReviewRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AddFileReviewService {

    @Autowired
    FileReviewRepo fileReviewRepo ;
    @Autowired
    GroupFeignController groupFeignController ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public String getLastModerator (String groupId){
        return fileReviewRepo.findFirstByGroupIdOrderByCreatedAtDesc(groupId)
                .map(FileReview::getModerator)
                .orElse(null);
    }
    public String assignModerator (String groupId){
        int last_moderator_index ;
        String new_moderator ;
        List<String> moderators = groupFeignController.getGroupModerators(groupId , GroupRoles.MODERATOR) ;
        if( moderators == null || moderators.isEmpty()  ) return null ;
        int moderators_number = moderators.size() ;
        Collections.sort(moderators);
        String last_moderator = getLastModerator(groupId) ;
        if (last_moderator == null) last_moderator_index = 0 ;
        else{
            int index = moderators.lastIndexOf(last_moderator) ;
            if(index == -1) last_moderator_index = 0 ;
            else {
                last_moderator_index = index ;
            }
        }
        new_moderator = moderators.get( (last_moderator_index + 1) % moderators_number) ;

        return new_moderator ;
    }

    public GetFileReview createReview (CreateReviewDto dto ){

        FileReview fileReview = modelMapper.map(dto , FileReview.class) ;
        fileReview.setLocked(false);
        fileReview.setStatus(Status.IN_REVIEW);
        fileReview.setModerator(assignModerator(dto.getGroupId()));
        FileReview added = fileReviewRepo.save(fileReview) ;
        return modelMapper.map(added , GetFileReview.class) ;
    }


}

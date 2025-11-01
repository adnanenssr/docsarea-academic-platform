package com.docsarea.service;

import com.docsarea.controller.FileFeignController;
import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.review.GetFileReview;
import com.docsarea.dtos.review.UpdateReview;
import com.docsarea.enums.GroupRoles;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.FileReview;
import com.docsarea.repository.FileReviewRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileReviewService {
    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    FileReviewRepo fileReviewRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    FileFeignController fileFeignController ;

    public void checkMemberPermission(String groupId , String username){
        MemberDto member = groupFeignController.getMember(groupId , username);
        if(member != null && member.getRole().isHigherThan(GroupRoles.MEMBER) && member.isReviewFile()) return ;
        throw new RuntimeException("you do not have permission to review this file");
    }
    public void checkReview(FileReview fileReview){
        if(fileReview.getStatus() != Status.IN_REVIEW ) throw new RuntimeException("this file have already been reviewed ") ;
        if(fileReview.isLocked()) throw new RuntimeException("this file is currently being reviewed by another qualified member") ;
    }

    public GetFileReview updateReview(UpdateReview dto , String groupId , String fileId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        FileReview fileReview = fileReviewRepo.findByFileId(fileId).orElseThrow(() -> new RuntimeException("Review is not Found")) ;
        checkMemberPermission(groupId , user);
        checkReview(fileReview);
        fileReview.setStatus(dto.getStatus());
        fileReview.setComment(dto.getComment());
        fileReview.setModerator(user);
        FileReview updated = fileReviewRepo.save(fileReview) ;
        fileFeignController.updateStatus(fileId, updated.getStatus());
        return modelMapper.map(updated , GetFileReview.class) ;
    }



}

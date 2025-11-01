package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.controller.ReviewFeignController;
import com.docsarea.dtos.file.FileModeratorDto;
import com.docsarea.dtos.file.FileModeratorPageDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.review.FileIdModeratorDto;
import com.docsarea.dtos.review.ReviewPageDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetReviews {

    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    ReviewFeignController reviewFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;


    public MemberDto hasPermission(String username , String groupId){
        MemberDto member = groupFeignController.getMember(groupId , username) ;
        if(!member.isReviewFile()) throw new RuntimeException("you don't have review permission") ;
        return member ;
    }

    public FileModeratorPageDto getPublished (String groupId , int page ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        ReviewPageDto filesPage ;
        List<FileModeratorDto> files ;
        MemberDto member = hasPermission(user , groupId);
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)){
            filesPage = reviewFeignController.getGroupReviews(groupId , Status.ACCEPTED , page) ;
            Map<String , String> reviewData = filesPage.getReviews().stream().filter(r -> r.getFileId() != null).collect(Collectors.toMap(FileIdModeratorDto::getFileId,
                    r -> r.getModerator() == null ? "" : r.getModerator())) ;
            files = fileRepo.findAllById(reviewData.keySet()).stream().map(doc -> {
                FileModeratorDto dto = modelMapper.map(doc , FileModeratorDto.class) ;
                dto.setModerator(reviewData.get(doc.getId()));
                return dto ;
            }).toList() ;
        }
        else {
            filesPage = reviewFeignController.getModeratorReviews(groupId , Status.ACCEPTED , page) ;
            Set<String> fileIds = filesPage.getReviews().stream().map(FileIdModeratorDto::getFileId).collect(Collectors.toSet());
            files = fileRepo.findAllById(fileIds).stream().map((doc) -> modelMapper.map(doc , FileModeratorDto.class)).toList() ;
        }
        FileModeratorPageDto filePage = new FileModeratorPageDto() ;
        filePage.setFiles(files);
        filePage.setNumPages(filesPage.getNumPages());
        filePage.setNumElements(filesPage.getNumElements());
        return filePage ;
    }

    public FileModeratorPageDto getInReview (String groupId , int page ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        ReviewPageDto filesPage ;
        List<FileModeratorDto> files ;
        MemberDto member = hasPermission(user , groupId);
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)){
            filesPage = reviewFeignController.getGroupReviews(groupId , Status.IN_REVIEW , page) ;
            Map<String , String> reviewData = filesPage.getReviews().stream().filter(r -> r.getFileId() != null).collect(Collectors.toMap(FileIdModeratorDto::getFileId,
                    r -> r.getModerator() == null ? "" : r.getModerator())) ;
            files = fileRepo.findAllById(reviewData.keySet()).stream().map(doc -> {
                FileModeratorDto dto = modelMapper.map(doc , FileModeratorDto.class) ;
                dto.setModerator(reviewData.get(doc.getId()));
                return dto ;
            }).toList() ;
        }
        else {
            filesPage = reviewFeignController.getModeratorReviews(groupId , Status.IN_REVIEW , page) ;

            Set<String> fileIds = filesPage.getReviews().stream().map(FileIdModeratorDto::getFileId).collect(Collectors.toSet());
            files = fileRepo.findAllById(fileIds).stream().map((doc) -> modelMapper.map(doc , FileModeratorDto.class)).toList() ;
        }
        FileModeratorPageDto filePage = new FileModeratorPageDto() ;
        filePage.setFiles(files);
        filePage.setNumPages(filesPage.getNumPages());
        filePage.setNumElements(filesPage.getNumElements());
        return filePage ;
    }

    public FileModeratorPageDto getRejected (String groupId , int page ){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        ReviewPageDto filesPage ;
        List<FileModeratorDto> files ;
        MemberDto member = hasPermission(user , groupId);
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)){
            filesPage = reviewFeignController.getGroupReviews(groupId , Status.REJECTED , page) ;
            Map<String , String> reviewData = filesPage.getReviews().stream().filter(r -> r.getFileId() != null).collect(Collectors.toMap(FileIdModeratorDto::getFileId,
                    r -> r.getModerator() == null ? "" : r.getModerator())) ;
            files = fileRepo.findAllById(reviewData.keySet()).stream().map(doc -> {
                FileModeratorDto dto = modelMapper.map(doc , FileModeratorDto.class) ;
                dto.setModerator(reviewData.get(doc.getId()));
                return dto ;
            }).toList() ;
        }
        else {
            filesPage = reviewFeignController.getModeratorReviews(groupId , Status.REJECTED , page) ;
            Set<String> fileIds = filesPage.getReviews().stream().map(FileIdModeratorDto::getFileId).collect(Collectors.toSet());
            files = fileRepo.findAllById(fileIds).stream().map((doc) -> modelMapper.map(doc , FileModeratorDto.class)).toList() ;
        }
        FileModeratorPageDto filePage = new FileModeratorPageDto() ;
        filePage.setFiles(files);
        filePage.setNumPages(filesPage.getNumPages());
        filePage.setNumElements(filesPage.getNumElements());
        return filePage ;
    }
}

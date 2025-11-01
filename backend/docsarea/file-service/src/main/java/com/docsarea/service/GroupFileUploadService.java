package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.controller.ReviewFeignController;
import com.docsarea.dtos.file.FileUploadDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.review.CreateReviewDto;
import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GroupFileUploadService {

    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    FileEntityMapper fileMapper ;
    @Autowired
    ReviewFeignController reviewFeignController ;
    @Autowired
    UploadService uploadService ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;


    public FileUploadPermission checkFileUploadStrategy(String groupId , String username){
        MemberDto member = groupFeignController.getMember(groupId , username);
        if(member == null ) throw new RuntimeException("member does not exists") ;
        return member.getUploadPermission() ;
    }

    public Document autoPublishPermissionHandler(FileUploadDto dto){
        dto.setStatus(Status.ACCEPTED);
        return fileMapper.toEntity(dto) ;
    }
    public Document reviewedPermissionHandler(FileUploadDto dto , String groupId){
        dto.setStatus(Status.IN_REVIEW);
        Document document = fileMapper.toEntity(dto) ;
        CreateReviewDto reviewDto = new CreateReviewDto() ;
        reviewDto.setFileId(document.getId());
        reviewDto.setGroupId(groupId) ;
        reviewFeignController.sendReview(reviewDto) ;
        return document ;
    }

    public Document uploadPermissionHandler(FileUploadDto dto , String groupId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        final FileUploadPermission permission = checkFileUploadStrategy(groupId , user) ;
        return switch (permission){
            case FORBIDDEN -> throw new RuntimeException("you are not permitted to upload files to this group");
            case AUTO_PUBLISH -> autoPublishPermissionHandler(dto) ;
            case REVIEWED -> reviewedPermissionHandler(dto , groupId) ;
        } ;
    }

    public GetFileDto groupUpload(FileUploadDto dto , MultipartFile file , String groupId){
        dto.setGroup(groupId);
        uploadService.verifyAuthorsAndShared(dto);
        Document entity = uploadPermissionHandler(dto , groupId) ;
        StorageFileDto storageDto = uploadService.storageUpload(entity.getId() , file) ;
        Document document = uploadService.mapFileDetails(entity , storageDto) ;
        Document uploaded = fileRepo.save(document) ;
        uploadService.elasticsearchUpload(uploaded);
        return modelMapper.map(uploaded , GetFileDto.class) ;
    }



}

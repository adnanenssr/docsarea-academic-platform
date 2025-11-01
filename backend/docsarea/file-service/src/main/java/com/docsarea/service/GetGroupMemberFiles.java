package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.file.FilePageDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGroupMemberFiles {
    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;



    public FilePageDto getPublished(String groupId ,int page){
        Page<Document> filesPage ;
        Pageable pageable = PageRequest.of(page , 20) ;
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        filesPage = fileRepo.findByOwnerAndGroupIdAndStatus(user , groupId , Status.ACCEPTED , pageable) ;
        List<GetFileDto> dox = filesPage.getContent().stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;

        FilePageDto files = new FilePageDto() ;
        files.setNumElements(filesPage.getTotalElements());
        files.setNumPages(filesPage.getTotalPages());
        files.setFiles(dox);

        return files ;
    }

    public FilePageDto getInReview(String groupId ,int page){
        Page<Document> filesPage ;
        Pageable pageable = PageRequest.of(page , 20) ;
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        filesPage = fileRepo.findByOwnerAndGroupIdAndStatus(user , groupId , Status.IN_REVIEW , pageable) ;

        List<GetFileDto> dox = filesPage.getContent().stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;

        FilePageDto files = new FilePageDto() ;
        files.setNumElements(filesPage.getTotalElements());
        files.setNumPages(filesPage.getTotalPages());
        files.setFiles(dox);

        return files ;
    }

    public FilePageDto getRejected(String groupId ,int page){
        Page<Document> filesPage ;
        Pageable pageable = PageRequest.of(page , 20) ;
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        filesPage = fileRepo.findByOwnerAndGroupIdAndStatus(user , groupId , Status.REJECTED , pageable) ;
        List<GetFileDto> dox = filesPage.getContent().stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;

        FilePageDto files = new FilePageDto() ;
        files.setNumElements(filesPage.getTotalElements());
        files.setNumPages(filesPage.getTotalPages());
        files.setFiles(dox);

        return files ;
    }



}

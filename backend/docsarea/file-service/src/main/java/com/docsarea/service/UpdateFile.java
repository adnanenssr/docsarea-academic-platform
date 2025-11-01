package com.docsarea.service;

import com.docsarea.controller.StorageFeignController;
import com.docsarea.dtos.file.FileUploadDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.elastic.FileElsRepo;
import com.docsarea.enums.Status;
import com.docsarea.exception.ForbiddenResourceException;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.module.ElsDocument;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;

@Service
public class UpdateFile {
    @Autowired
    FileRepo fileRepo ;
    @Autowired
    StorageFeignController storageFeignController ;
    @Autowired
    FileElsRepo fileElsRepo ;

    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public GetFileDto updateFile(MultipartFile file , String fileId){

        Document document = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        if(!isUpdatable(document)) {
            throw new ForbiddenResourceException("You Don't Have Permissions To Access This Resource ") ;
        }

        StorageFileDto dto = storageFeignController.update(file , fileId) ;

        document.setFilePath(dto.getFilePath());
        document.setFileName(dto.getOriginalFileName());
        document.setStorage(dto.getStorage());
        document.setExtension(dto.getExtension());

        Document newDocument = fileRepo.save(document) ;
        ElsDocument elsDocument = modelMapper.map(newDocument , ElsDocument.class) ;
        fileElsRepo.save(elsDocument) ;

        return modelMapper.map(newDocument , GetFileDto.class) ;

    }

    public GetFileDto updateDetails(FileUploadDto dto , String id){

        Document document = fileRepo.findById(id).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        if(!isUpdatable(document))
        {
            throw new ForbiddenResourceException("You Don't Have Permissions To Access This Resource ") ;
        }
        document.setTitle(dto.getTitle());
        document.setDescription(dto.getDescription());
        document.setAccessibility(dto.getAccessibility());
        document.setDownloadable(dto.isDownloadable());
        document.setAuthors(dto.getAuthors());
        document.setShared(dto.getShared());

        Document newDoc = fileRepo.save(document) ;
        ElsDocument elsDocument = modelMapper.map(newDoc , ElsDocument.class) ;
        fileElsRepo.save(elsDocument) ;

        return modelMapper.map(newDoc , GetFileDto.class) ;

    }

    public boolean isUpdatable( Document entity){
        return( entity.getGroup() == null || (entity.getGroup() != null && entity.getStatus() == Status.REJECTED) ) && entity.getOwner().equals(CurrentAuthenticatedUser.getCurrentAuthenticatedUser());
    }

    public void updateStatus(String fileId , Status status){
        Document document = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        document.setStatus(status);
        fileRepo.save(document) ;
        ElsDocument elsDocument = modelMapper.map(document , ElsDocument.class) ;
        fileElsRepo.save(elsDocument) ;
    }


}


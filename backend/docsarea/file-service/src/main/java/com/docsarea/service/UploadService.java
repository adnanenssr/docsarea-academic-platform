package com.docsarea.service;

import com.docsarea.controller.StorageFeignController;
import com.docsarea.controller.UserFeignController;
import com.docsarea.dtos.file.FileUploadDto;
import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.file.UserFileUploadDto;
import com.docsarea.elastic.FileElsRepo;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.module.ElsDocument;
import com.docsarea.repository.FileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UploadService  {
    @Autowired
    FileRepo fileRepo ;
    @Autowired
    StorageFeignController storageFeignController ;

    @Autowired
    UserFeignController userFeignController ;

    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    @Autowired
    FileEntityMapper fileMapper ;
    @Autowired
    FileElsRepo fileElsRepo ;


    public void verifyUsers(Set<String> users){

        if (userFeignController.isExistUsers(users)) return ;
        throw new RuntimeException("authors or shared does not exist") ;
    }

    public StorageFileDto storageUpload(String id , MultipartFile file){
        return storageFeignController.upload(id , file) ;
    }

    public void verifyAuthorsAndShared (FileUploadDto dto){
        if(dto.getAuthors() != null || dto.getShared() != null) {
            Set<String> users = new HashSet<>();
            if(dto.getAuthors() != null ) users.addAll(dto.getAuthors());
            if(dto.getShared() != null ) users.addAll(dto.getShared()) ;
            if( !users.isEmpty() ) verifyUsers(users);
        }
    }

    public void elasticsearchUpload (Document document){
        ElsDocument elsDocument = modelMapper.map(document, ElsDocument.class) ;
        fileElsRepo.save(elsDocument) ;
    }

    public Document mapFileDetails(Document entity , StorageFileDto dto){
        entity.setExtension(dto.getExtension());
        entity.setFilePath(dto.getFilePath());
        entity.setFileName(dto.getOriginalFileName());
        entity.setStorage(dto.getStorage());
        entity.setThumbnail(dto.getThumbnail());
        return entity ;
    }

}

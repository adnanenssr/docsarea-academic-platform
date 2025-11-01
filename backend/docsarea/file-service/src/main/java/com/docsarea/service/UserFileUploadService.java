package com.docsarea.service;

import com.docsarea.dtos.file.FileUploadDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.file.StorageFileDto;
import com.docsarea.dtos.file.UserFileUploadDto;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserFileUploadService {

    @Autowired
    UploadService  uploadService ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper();
    @Autowired
    FileEntityMapper fileMapper ;


    public GetFileDto userUpload(FileUploadDto dto , MultipartFile file){
        dto.setStatus(Status.ACCEPTED);
        dto.setGroup(null);
        uploadService.verifyAuthorsAndShared(dto);
        Document entity = fileMapper.toEntity(dto) ;
        StorageFileDto storageDto = uploadService.storageUpload(entity.getId() , file) ;
        Document document = uploadService.mapFileDetails(entity , storageDto) ;
        Document uploaded = fileRepo.save(document) ;
        uploadService.elasticsearchUpload(uploaded);
        return modelMapper.map(uploaded , GetFileDto.class) ;
    }

}

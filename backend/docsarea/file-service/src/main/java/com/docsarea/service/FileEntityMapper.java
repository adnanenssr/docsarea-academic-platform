package com.docsarea.service;

import com.docsarea.dtos.file.FileUploadDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.utility.CurrentAuthenticatedUser;
import com.github.f4b6a3.ulid.UlidCreator;
import org.apache.commons.fileupload.FileUpload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileEntityMapper {


    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public Document toEntity (FileUploadDto dto){
        Document entity = modelMapper.map(dto , Document.class) ;
        entity.setId(UlidCreator.getUlid().toString());
        entity.setOwner(CurrentAuthenticatedUser.getCurrentAuthenticatedUser()) ;
        return entity ;
    }
}

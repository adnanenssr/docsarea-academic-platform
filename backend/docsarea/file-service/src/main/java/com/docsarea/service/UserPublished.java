package com.docsarea.service;

import com.docsarea.dtos.file.FilePageDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.enums.Accessibility;
import com.docsarea.enums.Status;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserPublished {
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FilePageDto getUserPublished(String username , int page){
        Pageable pageable = PageRequest.of(page , 10) ;
        Accessibility[] accessibilities = {Accessibility.USERS , Accessibility.PUBLIC} ;

        List<Accessibility> accessibility= Arrays.asList(accessibilities) ;

        Page<Document> pageFiles = fileRepo.findByOwnerAndGroupIdIsNullAndStatusAndAccessibilityIn(username , Status.ACCEPTED  , pageable , accessibility ) ;
        List<GetFileDto> files = pageFiles.stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;
        FilePageDto filePage = new FilePageDto() ;
        filePage.setNumPages(pageFiles.getTotalPages());
        filePage.setNumElements(pageFiles.getTotalElements());
        filePage.setFiles(files);
        return filePage ;
    }

}

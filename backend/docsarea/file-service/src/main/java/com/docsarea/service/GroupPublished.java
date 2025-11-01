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

import java.util.List;

@Service
public class GroupPublished {
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FilePageDto getGroupPublished(String groupId , int page){
        Pageable pageable = PageRequest.of(page , 10) ;
        Page<Document> pageFiles = fileRepo.findByGroupIdAndStatusAndAccessibility(groupId , Status.ACCEPTED  , pageable , Accessibility.PUBLIC ) ;
        List<GetFileDto> files = pageFiles.stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;
        FilePageDto filePage = new FilePageDto() ;
        filePage.setNumPages(pageFiles.getTotalPages());
        filePage.setNumElements(pageFiles.getTotalElements());
        filePage.setFiles(files);
        return filePage ;
    }
}

package com.docsarea.service;

import com.docsarea.dtos.file.FilePageDto;
import com.docsarea.dtos.file.GetFileDto;
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
public class UserFilePageService {
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FilePageDto getUserPage(int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page , 20) ;
        Page <Document> docPage = fileRepo.findByOwnerAndGroupIdIsNullOrderByModifiedAtDesc(user , pageable) ;
        List<GetFileDto> documents = docPage.stream().map((file) -> modelMapper.map(file , GetFileDto.class)).toList() ;
        FilePageDto filePage = new FilePageDto() ;
        filePage.setFiles(documents);
        filePage.setNumPages(docPage.getTotalPages());
        filePage.setNumElements(docPage.getTotalElements());
        return filePage ;
    }

}

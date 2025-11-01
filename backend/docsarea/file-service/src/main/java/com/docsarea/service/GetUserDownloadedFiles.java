package com.docsarea.service;

import com.docsarea.controller.SocialFeignController;
import com.docsarea.dtos.file.FilePageDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.social.DownloadDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserDownloadedFiles {
    @Autowired
    SocialFeignController socialFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FilePageDto getDownloads(int page){
        DownloadDto dto = socialFeignController.getDownloads(page) ;
        List<Document> documents = fileRepo.findAllById(dto.getDownload()) ;
        List<GetFileDto> files = documents.stream().map(doc -> modelMapper.map(doc , GetFileDto.class)).toList() ;
        return new FilePageDto(files , dto.getNumElements() , dto.getNumPages()) ;
    }
}

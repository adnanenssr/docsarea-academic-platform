package com.docsarea.service;

import com.docsarea.dtos.file.FilePageDto;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.module.ElsDocument;
import com.docsarea.elastic.FileElsRepo;
import com.docsarea.repository.FileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    FileElsRepo fileElsRepo ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FilePageDto search(String query , int page){
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<ElsDocument> files = fileElsRepo.searchByQuery(query , pageable);
        List<GetFileDto> docs = files.getContent().stream().map(document -> modelMapper.map(document , GetFileDto.class)).toList() ;

        return new FilePageDto(docs , files.getTotalElements() , files.getTotalPages()) ;
    }

    public void migrate (){
        List<Document> all = fileRepo.findAll() ;
        List<ElsDocument> elsDocuments = all.stream().map(doc -> modelMapper.map(doc , ElsDocument.class)).toList() ;
        fileElsRepo.saveAll(elsDocuments) ;
    }

    public List<GetFileDto> getRecommendations(String query , String fileId){
        Pageable pageable = PageRequest.of(0 , 10) ;
        return fileElsRepo.searchWithFallback(query , fileId , pageable).getContent().stream().map(elsDocument -> modelMapper.map(elsDocument , GetFileDto.class)).toList() ;

    }



}

package com.docsarea.service;

import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeToStorage {
    @Autowired
    FileRepo fileRepo ;

    public void migrate(){
        List<Document> all = fileRepo.findAll() ;
        List<Document> documents = all.stream().map((document) -> {
            document.setStorage(Long.valueOf(document.getStorage()));
            return document ;
        }).toList() ;
        fileRepo.saveAll(documents) ;
    }
}

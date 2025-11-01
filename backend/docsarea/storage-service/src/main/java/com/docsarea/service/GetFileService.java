package com.docsarea.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class GetFileService {

    public Resource getFile( String id , String path){
        return new FileSystemResource(path) ;
    }
}

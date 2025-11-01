package com.docsarea.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeleteFile {

    public boolean deleteFile (String path){
        File file = new File(path) ;
        if (!file.exists()) {
            return true;
        }
            return file.delete() ;
    }
}

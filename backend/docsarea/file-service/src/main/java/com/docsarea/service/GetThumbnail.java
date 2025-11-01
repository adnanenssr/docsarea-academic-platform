package com.docsarea.service;

import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class GetThumbnail {
    @Autowired
    FileRepo fileRepo ;

    public Resource getThumbnail(String fileId){
        Document document = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("this file does not exist")) ;

        String filePath = document.getThumbnail() != null ? document.getThumbnail() : "Upload/adminx/01K7GV21WNWT11HZZP84C3P8H2.jpg" ;
        Path path = Path.of(filePath).toAbsolutePath() ;

        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);
    }
}

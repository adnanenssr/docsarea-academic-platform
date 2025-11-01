package com.docsarea.service;

import com.docsarea.controller.FileFeignController;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.file.StorageFileDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUpdateService {
    @Autowired
    FileFeignController fileFeignController ;

    public GetFileDto updateFile(MultipartFile file , String id){
        return fileFeignController.updateFile(file , id) ;
    }
}

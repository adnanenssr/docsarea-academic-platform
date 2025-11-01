package com.docsarea.service;

import com.docsarea.dtos.social.DownloadDto;
import com.docsarea.model.Download;
import com.docsarea.repository.DownloadRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDownloads {
    @Autowired
    DownloadRepo downloadRepo ;

    public DownloadDto getDownloads ( int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<String> files = downloadRepo.getUserDownloads(user , pageable) ;
        return new DownloadDto(files.getContent() , files.getTotalPages() , files.getTotalElements()) ;
    }
}

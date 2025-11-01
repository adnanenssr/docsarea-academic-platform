package com.docsarea.service;

import com.docsarea.model.Download;
import com.docsarea.repository.DownloadRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddDownload {
    @Autowired
    DownloadRepo downloadRepo ;
    public void addDownload(String fileId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Download download = new Download(user , fileId) ;
        downloadRepo.save(download) ;
    }
}

package com.docsarea.service;

import com.docsarea.model.Bookmark;
import com.docsarea.repository.BookmarkRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudBookmark {
    @Autowired
    BookmarkRepo bookmarkRepo ;
    public void addBookmark(String fileId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Bookmark bookmark = new Bookmark(user , fileId) ;
        bookmarkRepo.save(bookmark) ;
    }

    public void removeBookmark(String fileId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Bookmark bookmark = bookmarkRepo.findByFileIdAndUsername(fileId , user).orElseThrow(() -> new RuntimeException("bookmark does not exist")) ;
        bookmarkRepo.delete(bookmark);
    }

    public boolean existsBookmark(String fileId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        return bookmarkRepo.existsByUsernameAndFileId(user , fileId) ;
    }
}

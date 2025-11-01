package com.docsarea.service;

import com.docsarea.dtos.social.BookmarkDto;
import com.docsarea.repository.BookmarkRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class GetBookmarks {
    @Autowired
    BookmarkRepo bookmarkRepo ;

    public BookmarkDto getBookmarks(int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Pageable pageable = PageRequest.of(page , 20) ;
        Page<String> files = bookmarkRepo.getUserBookmarks(user , pageable) ;
        return new BookmarkDto(files.getContent() , files.getTotalPages() ,files.getTotalElements()) ;
    }


}

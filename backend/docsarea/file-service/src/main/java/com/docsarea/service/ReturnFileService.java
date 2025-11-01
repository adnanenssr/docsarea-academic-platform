package com.docsarea.service;

import com.docsarea.controller.*;
import com.docsarea.dtos.file.GetFileDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.Accessibility;
import com.docsarea.enums.GroupRoles;
import com.docsarea.enums.Status;
import com.docsarea.exception.ForbiddenResourceException;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ReturnFileService {
    @Autowired
    FileRepo fileRepo ;

    @Autowired
    StorageFeignController storageFeignController ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    MetricsFeignController metricsFeignController ;
    @Autowired
    GroupFeignController groupFeignController ;
    @Autowired
    ReviewFeignController reviewFeignController ;
    @Autowired
    SocialFeignController socialFeignController ;

    public Resource getFile(String id){
        Document document = fileRepo.findById(id).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        String publisher ;
        if(document.getGroup() == null ) publisher = document.getOwner() ;
        else publisher = document.getGroup();
        validateAccess(document);
        validateStatus(document);
        addView( publisher ,document.getId() , document.getOwner() );

        File file = new File(document.getFilePath());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }

    public GetFileDto getDetails(String id){
        Document document = fileRepo.findById(id).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        validateStatus(document);
        validateAccess(document);
        return modelMapper.map(document , GetFileDto.class) ;

    }

    public boolean haveAccess (Document entity){
        return (entity.getOwner().equals(CurrentAuthenticatedUser.getCurrentAuthenticatedUser())
        || entity.getAuthors().contains(CurrentAuthenticatedUser.getCurrentAuthenticatedUser())
        || entity.getShared().contains(CurrentAuthenticatedUser.getCurrentAuthenticatedUser())) ;
    }

    public void validateAccess (Document entity){

        switch (entity.getAccessibility()){
            case Accessibility.PUBLIC : return ;
            case Accessibility.PRIVATE:
                if(entity.getOwner().equals(CurrentAuthenticatedUser.getCurrentAuthenticatedUser())) return ;
                else throw new ForbiddenResourceException("This Resource Is Private") ;
            case Accessibility.USERS:
                Authentication auth = SecurityContextHolder.getContext().getAuthentication() ;
                if(auth instanceof JwtAuthenticationToken) return ;
                else throw new ForbiddenResourceException("Please Login Or Create An Account To See This Resource") ;
            case Accessibility.SHARED:
                if(haveAccess(entity)) return ;
                else throw new ForbiddenResourceException("You Don't Have Permission To Access This Resource") ;


        }
    }

    public void validateStatus (Document entity) {

        if(entity.getStatus() == Status.ACCEPTED) return ;
        if(entity.getStatus() == Status.IN_REVIEW && SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken){
            String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
            if( entity.getOwner().equals(user)) return ;
            MemberDto member = groupFeignController.getMember(entity.getGroup() , user) ;
            if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return ;
            if(member.getRole() == GroupRoles.MODERATOR && reviewFeignController.isModerator(entity.getId())) return ;

        }
        throw new ForbiddenResourceException("This Resource Can Not Be Accessed") ;
    }

    public void isDownloadable (Document entity){
        if(entity.isDownloadable()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication() ;
            if(auth instanceof  JwtAuthenticationToken) return ;
            else throw new ForbiddenResourceException("Please Login Or Create An Account To Download This Resource") ;
        }else throw new ForbiddenResourceException("This Resource Is Not For Download ") ;
    }

    public Resource downloadFile(String id){
        Document document = fileRepo.findById(id).orElseThrow(() -> new RuntimeException("this file does not exist")) ;
        validateStatus(document);
        validateAccess(document);
        isDownloadable(document);
        String publisher ;
        if(document.getGroup() == null ) publisher = document.getOwner() ;
        else publisher = document.getGroup();
        addDownload(publisher , document.getId() , document.getOwner());
        socialFeignController.addDownload(document.getId());
        File file = new File(document.getFilePath());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);
    }

    public void addView(String publisher , String fileId , String owner){
        metricsFeignController.addView(fileId , publisher , owner);
    }

    public void addDownload(String publisher , String fileId , String owner){
        metricsFeignController.addDownload(fileId , publisher , owner);
    }






}

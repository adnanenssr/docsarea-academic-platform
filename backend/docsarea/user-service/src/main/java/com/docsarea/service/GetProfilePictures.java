package com.docsarea.service;

import com.docsarea.model.User;
import com.docsarea.repository.UserRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class GetProfilePictures {
    @Autowired
    UserRepo userRepo ;

    public Resource getCover (){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException(("user is not found "))) ;
        Path path = Path.of(user.getCover()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }

    public Resource getAvatar (){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException(("user is not found "))) ;
        Path path = Path.of(user.getProfilePicUrl()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }

    public Resource getUserCover (String username){
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException(("user is not found "))) ;
        Path path = Path.of(user.getCover()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }

    public Resource getUserAvatar (String username){
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException(("user is not found "))) ;
        Path path = Path.of(user.getProfilePicUrl()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }
}

package com.docsarea.service;

import com.docsarea.model.Group;
import com.docsarea.repository.GroupRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class GetProfilePictures {
    @Autowired
    GroupRepo groupRepo ;
    public Resource getCover (String groupId){
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("group not found")) ;
        Path path = Path.of(group.getCoverImg()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }

    public Resource getAvatar (String groupId){
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("group not found")) ;
        Path path = Path.of(group.getProfileImg()).toAbsolutePath() ;
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        // Wrap it as a FileSystemResource
        return new FileSystemResource(file);

    }
}

package com.docsarea.service;

import com.docsarea.controller.StorageFeignController;
import com.docsarea.dtos.user.EmailDto;
import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.UserInfoDto;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.User;
import com.docsarea.repository.UserRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UpdateUserService {
    @Autowired
    UserRepo userRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    PasswordEncoder passwordEncoder ;
    @Autowired
    StorageFeignController storageFeignController ;

    public GetDto updateEmail(EmailDto dto){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exists")) ;

        user.setEmail(dto.getEmail());

        User updatedUser = userRepo.save(user) ;
        return modelMapper.map(updatedUser , GetDto.class) ;
    }

    public GetDto updateInfo(UserInfoDto dto){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exists")) ;
        user.setBio(dto.getBio());
        user.setAddress(dto.getAddress());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());

        User updatedUser = userRepo.save(user) ;
        return modelMapper.map(updatedUser , GetDto.class) ;
    }

    public GetDto changePassword(String password){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exists")) ;
        user.setPassword(passwordEncoder.encode(password)) ;

        User updatedUser = userRepo.save(user) ;
        return modelMapper.map(updatedUser , GetDto.class) ;
    }

    public void updateAvatar (MultipartFile file) throws IOException {
        String path = storageFeignController.saveAvatar(file);
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exist")) ;
        user.setProfilePicUrl(path);
        userRepo.save(user) ;
    }

    public void updateCover (MultipartFile file) throws IOException {
        String path = storageFeignController.saveCover(file) ;
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exist")) ;
        user.setCover(path);
        userRepo.save(user) ;
    }

    public void deleteCover () {
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exist")) ;
        user.setCover("");
    }

    public void deleteAvatar () {
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("this user does not exist")) ;
        user.setProfilePicUrl("");
    }







}

package com.docsarea.controller;

import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserFeignController userFeignController ;

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<GetDto> getUser(@PathVariable String username ){
        GetDto user = userFeignController.getUser(username) ;
        return new ResponseEntity<>(user , HttpStatus.OK) ;
    }

    @GetMapping(value = "/get/user")
    public GetDto getThisUser( ){
        return userFeignController.getThisUser() ;
    }

    @PutMapping(value = "/api/update/info")
    public GetDto userInfoUpdate (@RequestBody UserInfoDto dto){
        return userFeignController.userInfoUpdate(dto) ;
    }

    @DeleteMapping(value = "/api/delete/cover")
    public void deleteCover (){
        userFeignController.deleteCover();
    }

    @DeleteMapping(value = "/api/delete/avatar")
    public void deleteAvatar (){
        userFeignController.deleteAvatar();
    }

    @GetMapping(value = "/api/get/cover")
    public ResponseEntity<Resource> getCover (){
        return userFeignController.getCover() ;
    }
    @GetMapping(value = "/api/get/avatar")
    public ResponseEntity<Resource> getAvatar (){
        return userFeignController.getAvatar() ;
    }

    @GetMapping(value = "/api/get/cover/{username}")
    public ResponseEntity<Resource> getUserCover (@PathVariable String username){
        return userFeignController.getUserCover(username) ;
    }
    @GetMapping(value = "/api/get/avatar/{username}")
    public ResponseEntity<Resource> getUserAvatar (@PathVariable String username){
        return userFeignController.getUserAvatar(username) ;
    }


    @GetMapping(value = "/user/suggestions")
    public List<String> getSuggestions (@RequestParam("value") String val){
        return userFeignController.getSuggestions(val) ;
    }
    @PutMapping(value = "/api/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar (@RequestPart("file") MultipartFile file) throws IOException {
        userFeignController.saveAvatar(file);
    }

    @PutMapping(value = "/api/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveCover (@RequestPart("file") MultipartFile file) throws IOException {
        userFeignController.saveCover(file);

    }

}

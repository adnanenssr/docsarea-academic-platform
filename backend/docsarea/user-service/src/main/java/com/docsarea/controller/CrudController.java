package com.docsarea.controller;

import com.docsarea.dtos.user.*;
import com.docsarea.service.*;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class CrudController {
    @Autowired
    RegisterService registerService ;
    @Autowired
    LoginService loginService ;
    @Autowired
    ExistsUsersService existsUsersService ;
    @Autowired
    UpdateUserService updateUserService ;
    @Autowired
    GetUserService getUserService ;
    @Autowired
    GetProfilePictures getProfilePictures ;
    @Autowired
    GetSuggestions suggestions ;
    @Autowired
    DeleteUser deleteUser ;

    @PostMapping(value = "/api/register" , consumes = "Application/json" , produces = "Application/json")
    public ResponseEntity<GetDto> register ( @RequestBody RegisterDto registerDto) {
        GetDto dto = registerService.saveUser(registerDto) ;
        return new ResponseEntity<>(dto , HttpStatus.ACCEPTED) ;
    }

    @PostMapping(value = "/api/login")
    public ResponseEntity<GetDto> login (@RequestBody LoginDto dto ){
         GetDto getDto = loginService.getUser(dto.getUsername()) ;
         return new ResponseEntity<>(getDto , HttpStatus.ACCEPTED) ;
    }

    @GetMapping(value = "/test")
    public String test (){
        return CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
    }

    @PostMapping(value = "/api/exist")
    public boolean isUsersExist (@RequestBody Set<String> users ) {
        return existsUsersService.isExistUsers(users) ;
    }

    @PutMapping(value = "/api/update")
    public GetDto userUpdate (@RequestBody EmailDto dto){
        return updateUserService.updateEmail(dto) ;
    }

    @GetMapping(value = "/user/{username}")
    public GetDto getUser(@PathVariable String username ){
        return getUserService.getUser(username) ;
    }

    @GetMapping(value = "/get/user")
    public GetDto getThisUser(){
        return getUserService.getThisUser() ;
    }

    @PutMapping(value = "/api/update/info")
    public GetDto userInfoUpdate (@RequestBody UserInfoDto dto){
        return updateUserService.updateInfo(dto) ;
    }

    @PutMapping(value = "/api/update/password")
    public GetDto changePassword (@RequestBody String dto){
        return updateUserService.changePassword(dto) ;
    }

    @PutMapping(value = "/api/save/avatar")
    public void saveAvatar (@RequestPart("file") MultipartFile file) throws IOException {
        updateUserService.updateAvatar(file);
    }

    @PutMapping(value = "/api/save/cover")
    public void saveCover (@RequestPart("file") MultipartFile file) throws IOException {
        updateUserService.updateCover(file);

    }

    @DeleteMapping(value = "/api/delete/cover")
    public void deleteCover (){
        updateUserService.deleteCover();
    }

    @DeleteMapping(value = "/api/delete/avatar")
    public void deleteAvatar (){
        updateUserService.deleteAvatar();
    }



    @GetMapping(value = "/api/get/cover")
    public ResponseEntity<Resource> getCover (){
        Resource cover = getProfilePictures.getCover() ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(cover) ;
    }
    @GetMapping(value = "/api/get/avatar")
    public ResponseEntity<Resource> getAvatar (){
        Resource avatar = getProfilePictures.getAvatar() ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(avatar) ;
    }

    @GetMapping(value = "/api/get/cover/{username}")
    public ResponseEntity<Resource> getUserCover (@PathVariable String username){
        Resource cover = getProfilePictures.getUserCover(username) ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(cover) ;
    }
    @GetMapping(value = "/api/get/avatar/{username}")
    public ResponseEntity<Resource> getUserAvatar (@PathVariable String username){
        Resource avatar = getProfilePictures.getUserAvatar(username) ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(avatar) ;
    }

    @GetMapping(value = "/user/suggestions")
    public List<String> getSuggestions (@RequestParam("value") String val){
        return suggestions.getSuggestions(val) ;
    }

    @DeleteMapping(value = "/user/delete")
    public void deleteUser (){
        deleteUser.deleteThisUser();
    }






}

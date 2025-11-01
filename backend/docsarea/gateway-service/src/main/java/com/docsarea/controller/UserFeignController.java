package com.docsarea.controller;

import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@FeignClient("user-service")
public interface UserFeignController {
    @GetMapping(value = "/user/{username}")
    public GetDto getUser(@PathVariable String username);

    @GetMapping(value = "/get/user")
    public GetDto getThisUser();

    @PutMapping(value = "/api/update/info")
    public GetDto userInfoUpdate(@RequestBody UserInfoDto dto);

    @DeleteMapping(value = "/api/delete/cover")
    public void deleteCover();

    @DeleteMapping(value = "/api/delete/avatar")
    public void deleteAvatar();

    @GetMapping(value = "/api/get/cover")
    public ResponseEntity<Resource> getCover();

    @GetMapping(value = "/api/get/avatar")
    public ResponseEntity<Resource> getAvatar();
    @GetMapping(value = "/api/get/cover/{username}")
    public ResponseEntity<Resource> getUserCover(@PathVariable String username);

    @GetMapping(value = "/api/get/avatar/{username}")
    public ResponseEntity<Resource> getUserAvatar(@PathVariable String username);

    @GetMapping(value = "/user/suggestions")
    public List<String> getSuggestions (@RequestParam("value") String val);
    @PutMapping(value = "/api/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar (@RequestPart("file") MultipartFile file) throws IOException ;

    @PutMapping(value = "/api/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveCover (@RequestPart("file") MultipartFile file) throws IOException;
}
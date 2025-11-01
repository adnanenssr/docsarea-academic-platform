package com.docsarea.controller;

import com.docsarea.dtos.group.*;
import com.docsarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CrudController {

    @Autowired
    GetProfilePictures getProfilePictures ;
    @Autowired
    CreateUpdateGroupService createUpdateGroupService ;
    @Autowired
    GetGroupService getGroupService ;
    @Autowired
    GetGroups getGroups ;
    @Autowired
    RemoveMember removeMember ;
    @Autowired
    DeleteGroup deleteGroup ;
    @Autowired
    GetUserJoinedGroups getUserJoinedGroups ;
    @PostMapping (value = "/group/create")
    public GetGroupDto createGroup(@RequestBody CreateGroupDto dto){
        return createUpdateGroupService.saveGroup(dto);
    }

    @PutMapping(value = "/group/update/{id}")
    public GetGroupDto updateGroup(@RequestBody UpdateGroupDto dto , @PathVariable String id){
        return createUpdateGroupService.updateGroup(dto , id);
    }

    @GetMapping(value = "/group/{groupId}")
    public GroupInfoDto getGroup(@PathVariable String groupId){
        return getGroupService.getGroup(groupId) ;
    }

    @GetMapping(value = "/get/groups")
    public GroupPageDto getGroups(@RequestParam("page") int page){
        return getGroups.getGroups(page) ;
    }

    @GetMapping(value = "/group/{groupId}/get/cover")
    public ResponseEntity<Resource> getCover (@PathVariable String groupId){
        Resource cover = getProfilePictures.getCover(groupId) ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(cover) ;
    }
    @GetMapping(value = "/group/{groupId}/get/avatar")
    public ResponseEntity<Resource> getAvatar (@PathVariable String groupId){
        Resource avatar = getProfilePictures.getAvatar(groupId) ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(avatar) ;
    }
    @PutMapping(value = "/group/{groupId}/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar (@RequestParam("file")MultipartFile file , @PathVariable String groupId) throws IOException {
        createUpdateGroupService.updateAvatar(file , groupId );
    }

    @PutMapping(value = "/group/{groupId}/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveCover (@RequestParam("file")MultipartFile file , @PathVariable String groupId) throws IOException {
        createUpdateGroupService.updateCover(file , groupId);

    }

    @DeleteMapping(value = "/group/{groupId}/delete/cover")
    public void deleteCover (@PathVariable String groupId){
        createUpdateGroupService.deleteCover(groupId);
    }

    @DeleteMapping(value = "/group/{groupId}/delete/avatar")
    public void deleteAvatar (@PathVariable String groupId){
        createUpdateGroupService.deleteAvatar(groupId);
    }

    @PutMapping(value = "/group/ownership/update/{groupId}")
    public void transferOwnership(@PathVariable String groupId , @RequestParam("to") String newOwner){
        createUpdateGroupService.transferOwnership(groupId , newOwner);
    }

    @DeleteMapping(value = "/group/delete/{groupId}")
    public void deleteGroup(@PathVariable String groupId){
        deleteGroup.deleteGroup(groupId);
    }
    @DeleteMapping(value = "/delete/all/user/groups")
    public void deleteAllUserGroups(){
        deleteGroup.deleteAllUserGroups();
    }

    @GetMapping(value = "/get/user/joined/groups")
    public GroupPageDto getUserJoinedGroups(@RequestParam("page") int page){
        return getUserJoinedGroups.getUserJoinedGroups(page) ;
    }



}

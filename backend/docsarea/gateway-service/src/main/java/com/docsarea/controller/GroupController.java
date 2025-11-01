package com.docsarea.controller;

import com.docsarea.dtos.group.*;
import com.docsarea.dtos.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GroupController {

    @Autowired
    GroupFeignController groupFeignController ;

    @PostMapping(value = "/group/create")
    public ResponseEntity<GetGroupDto> createGroup(@RequestBody CreateGroupDto dto){
        GetGroupDto groupDto = groupFeignController.createGroup(dto) ;
        return new ResponseEntity<>(groupDto , HttpStatus.OK) ;
    }

    @PutMapping(value = "/group/update/{id}")
    public ResponseEntity<GetGroupDto> updateGroup(@RequestBody UpdateGroupDto dto , @PathVariable String id){
        GetGroupDto groupDto = groupFeignController.updateGroup(dto , id) ;
        return new ResponseEntity<>(groupDto , HttpStatus.OK) ;
    }

    @GetMapping(value = "/group/{groupId}")
    public ResponseEntity<GroupInfoDto> getGroup(@PathVariable String groupId) {
        GroupInfoDto group =  groupFeignController.getGroup(groupId ) ;
        return new ResponseEntity<>(group , HttpStatus.OK) ;
    }

    @GetMapping(value = "/get/groups")
    public GroupPageDto getGroups(@RequestParam("page") int page){
        return groupFeignController.getGroups(page) ;
    }
    @GetMapping(value = "/get/member/{groupId}")
    public MemberDto getThisMember(@PathVariable String groupId) {
        return groupFeignController.getThisMember(groupId) ;
    }

    @GetMapping(value = "/group/{groupId}/get/cover")
    public ResponseEntity<Resource> getCover (@PathVariable String groupId){
        return groupFeignController.getCover(groupId) ;
    }
    @GetMapping(value = "/group/{groupId}/get/avatar")
    public ResponseEntity<Resource> getAvatar (@PathVariable String groupId){
        return groupFeignController.getAvatar(groupId) ;
    }

    @DeleteMapping(value = "/group/{groupId}/delete/cover")
    public void deleteCover (@PathVariable String groupId){
        groupFeignController.deleteCover(groupId);
    }

    @DeleteMapping(value = "/group/{groupId}/delete/avatar")
    public void deleteAvatar (@PathVariable String groupId){
        groupFeignController.deleteAvatar(groupId);
    }

    @PutMapping(value = "/group/ownership/update/{groupId}")
    public void transferOwnership(@PathVariable String groupId , @RequestParam("to") String newOwner){
        groupFeignController.transferOwnership(groupId , newOwner);
    }

    @PutMapping(value = "/group/{groupId}/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException {
        groupFeignController.saveAvatar(file , groupId );
    }

    @PutMapping(value = "/group/{groupId}/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveCover (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException {
        groupFeignController.saveCover(file , groupId);

    }
    @DeleteMapping(value = "/group/delete/{groupId}")
    public void deleteGroup(@PathVariable String groupId){
        groupFeignController.deleteGroup(groupId);
    }

    @GetMapping(value = "/get/user/joined/groups")
    public GroupPageDto getUserJoinedGroups(@RequestParam("page") int page){
        return groupFeignController.getUserJoinedGroups(page) ;
    }

}

package com.docsarea.controller;

import com.docsarea.dtos.group.*;
import com.docsarea.dtos.invitation.CreateInvitationLinkDto;
import com.docsarea.dtos.invitation.GetInvitationLink;
import com.docsarea.dtos.invitation.GetLinkPageDto;
import com.docsarea.dtos.invitation.UpdateLinkDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.member.MemberPageDto;
import com.docsarea.dtos.member.UpdateMemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@FeignClient("group-service")
public interface GroupFeignController {
    @PostMapping(value = "/group/create")
    public GetGroupDto createGroup(@RequestBody CreateGroupDto dto) ;
    @PutMapping(value = "/group/update/{id}")
    public GetGroupDto updateGroup(@RequestBody UpdateGroupDto dto , @PathVariable String id) ;


    @PostMapping(value = "/{id}/member/add")
    public MemberDto addMember(@RequestBody MemberDto dto , @PathVariable String id);
    @GetMapping(value = "/invitation/{id}")
    public GroupInfoDto inviteMember(@PathVariable String id);
    @PostMapping(value = "/invitation/{id}/join")
    public GetGroupDto joinGroup(@PathVariable String id);
    @PutMapping(value = "/link/update/{id}")
    public GetInvitationLink updateLink(@RequestBody UpdateLinkDto dto, @PathVariable String id);
    @PutMapping(value = "/{username}/member/update/{groupId}")
    public MemberDto updateMember(@RequestBody UpdateMemberDto dto , @PathVariable String groupId , @PathVariable String username) ;
    @PostMapping (value = "/create/invitation/{groupId}")
    public GetInvitationLink createLink(@RequestBody CreateInvitationLinkDto dto , @PathVariable String groupId);
    @GetMapping(value = "/group/{groupId}")
    public GroupInfoDto getGroup(@PathVariable String groupId) ;
    @GetMapping(value = "/get/member/{groupId}/{username}")
    public MemberDto getMember(@PathVariable String groupId , @PathVariable String username) ;
    @GetMapping(value = "/get/links/{groupId}")
    public GetLinkPageDto getLinks(@PathVariable String groupId , @RequestParam("page") Integer page);
    @GetMapping(value = "/get/groups")
    public GroupPageDto getGroups(@RequestParam("page") int page);
    @GetMapping(value = "/get/members/{groupId}")
    public MemberPageDto getAllMembers(@RequestParam("page") int page , @PathVariable String groupId);
    @GetMapping(value = "/get/member/{groupId}")
    public MemberDto getThisMember(@PathVariable String groupId) ;
    @GetMapping(value = "/group/{groupId}/get/cover")
    public ResponseEntity<Resource> getCover (@PathVariable String groupId);
    @GetMapping(value = "/group/{groupId}/get/avatar")
    public ResponseEntity<Resource> getAvatar (@PathVariable String groupId);
    @DeleteMapping(value = "/group/{groupId}/delete/cover")
    public void deleteCover (@PathVariable String groupId) ;
    @DeleteMapping(value = "/group/{groupId}/delete/avatar")
    public void deleteAvatar (@PathVariable String groupId) ;
    @PutMapping(value = "/group/ownership/update/{groupId}")
    public void transferOwnership(@PathVariable String groupId , @RequestParam("to") String newOwner);
    @GetMapping(value = "/get/suggestions/{groupId}")
    public List<String> getSuggestions(@PathVariable String groupId , @RequestParam("value") String value);
    @PutMapping(value = "/group/{groupId}/save/avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException ;
    @PutMapping(value = "/group/{groupId}/save/cover" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveCover (@RequestPart("file") MultipartFile file , @PathVariable String groupId) throws IOException ;

    @DeleteMapping(value = "/member/delete/{username}/{groupId}")
    public void deleteMember(@PathVariable String username , @PathVariable String groupId);
    @DeleteMapping(value = "/group/delete/{groupId}")
    public void deleteGroup(@PathVariable String groupId);
    @GetMapping(value = "/get/user/joined/groups")
    public GroupPageDto getUserJoinedGroups(@RequestParam("page") int page);



}

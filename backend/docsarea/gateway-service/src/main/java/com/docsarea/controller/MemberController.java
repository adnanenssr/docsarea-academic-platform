package com.docsarea.controller;

import com.docsarea.dtos.group.GetGroupDto;
import com.docsarea.dtos.group.GroupInfoDto;
import com.docsarea.dtos.invitation.CreateInvitationLinkDto;
import com.docsarea.dtos.invitation.GetInvitationLink;
import com.docsarea.dtos.invitation.GetLinkPageDto;
import com.docsarea.dtos.invitation.UpdateLinkDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.member.MemberPageDto;
import com.docsarea.dtos.member.UpdateMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
public class MemberController {
    @Autowired
    GroupFeignController groupFeignController ;
    @PostMapping(value = "/{groupId}/member/add")
    public MemberDto addMember(@RequestBody MemberDto dto , @PathVariable String groupId){
        return groupFeignController.addMember(dto , groupId);
    }
    @GetMapping(value = "/invitation/{id}")
    public GroupInfoDto inviteMember(@PathVariable String id){
        return groupFeignController.inviteMember(id);
    }
    @PostMapping(value = "/invitation/{id}/join")
    public GetGroupDto joinGroup(@PathVariable String id){
        return groupFeignController.joinGroup(id) ;
    }
    @PostMapping(value = "/create/invitation/{groupId}")
    public GetInvitationLink createLink(@RequestBody CreateInvitationLinkDto dto , @PathVariable String groupId){
        return groupFeignController.createLink(dto , groupId) ;
    }

    @PutMapping(value = "/link/update/{id}")
    public GetInvitationLink updateLink(@RequestBody UpdateLinkDto dto, @PathVariable String id){
        return groupFeignController.updateLink(dto , id);
    }
    @PutMapping(value = "/{username}/member/update/{groupId}")
    public MemberDto updateMember(@RequestBody UpdateMemberDto dto , @PathVariable String groupId , @PathVariable String username){
        return groupFeignController.updateMember(dto , groupId , username);
    }

    @GetMapping(value = "/get/member/{groupId}/{username}")
    public MemberDto getMember(@PathVariable String groupId , @PathVariable String username){
        return groupFeignController.getMember(groupId , username) ;
    }

    @GetMapping(value = "/get/links/{groupId}")
    public GetLinkPageDto getLinks(@PathVariable String groupId, @RequestParam("page") Integer page){
        return groupFeignController.getLinks(groupId , page) ;
    }

    @GetMapping(value = "/get/members/{groupId}")
    public MemberPageDto getAllMembers(@RequestParam("page") int page , @PathVariable String groupId){
        return groupFeignController.getAllMembers(page , groupId) ;
    }
    @GetMapping(value = "/get/suggestions/{groupId}")
    public List<String> getSuggestions(@PathVariable String groupId , @RequestParam("value") String value){
        return groupFeignController.getSuggestions(groupId , value) ;
    }
    @DeleteMapping(value = "/member/delete/{username}/{groupId}")
    public void deleteMember(@PathVariable String username , @PathVariable String groupId){
        groupFeignController.deleteMember(username , groupId);
    }


}

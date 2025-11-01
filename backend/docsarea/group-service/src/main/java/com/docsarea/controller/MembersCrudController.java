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
import com.docsarea.enums.GroupRoles;
import com.docsarea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MembersCrudController {
    @Autowired
    AddMemberService addMemberService ;
    @Autowired
    InvitationLinkService invitationLinkService ;
    @Autowired
    UpdateLinkService updateLinkService ;
    @Autowired
    UpdateMemberService updateMemberService ;
    @Autowired
    GetMembers getMembers ;
    @Autowired
    CreateInvitationLinkService createInvitationLinkService ;
    @Autowired
    GetLinksService getLinksService ;
    @Autowired
    GetAllMembers getAllMembers ;
    @Autowired
    GetSuggestions getSuggestions ;
    @Autowired
    RemoveMember removeMember ;

    @PostMapping(value = "/{id}/member/add")
    public MemberDto addMember(@RequestBody MemberDto dto , @PathVariable String id){
        return addMemberService.addMember(dto , id) ;
    }
    @GetMapping(value = "/invitation/{id}")
    public GroupInfoDto inviteMember(@PathVariable String id){
        return invitationLinkService.joinGroup(id);
    }
    @PostMapping(value = "/invitation/{id}/join")
    public GetGroupDto joinGroup(@PathVariable String id){
        return invitationLinkService.addMember(id) ;
    }

    @PutMapping(value = "/link/update/{id}")
    public GetInvitationLink updateLink(@RequestBody UpdateLinkDto dto, @PathVariable String id){
        return updateLinkService.updateLink(id , dto) ;
    }
    @PutMapping(value = "/{username}/member/update/{groupId}")
    public MemberDto updateMember(@RequestBody UpdateMemberDto dto , @PathVariable String groupId , @PathVariable String username){
        return updateMemberService.updateMember(dto , groupId , username) ;
    }
    @GetMapping(value = "/get/members/{role}/{groupId}")
    public List<String> getModerators(@PathVariable String groupId , @PathVariable GroupRoles role){
        return getMembers.getMembers(groupId , role) ;
    }
    @GetMapping(value = "/get/member/{groupId}/{username}")
    public MemberDto getMember(@PathVariable String groupId , @PathVariable String username ){
        return getMembers.getMember(groupId, username);
    }

    @PostMapping(value = "/create/invitation/{groupId}")
    public GetInvitationLink createInvitation(@RequestBody CreateInvitationLinkDto dto , @PathVariable String groupId){
        return createInvitationLinkService.addLink(dto , groupId) ;
    }

    @GetMapping(value = "/get/links/{groupId}")
    public GetLinkPageDto getLinks(@PathVariable String groupId , @RequestParam("page") Integer page){
        return getLinksService.getLinks(groupId , page) ;
    }

    @GetMapping(value = "/get/members/{groupId}")
    public MemberPageDto getAllMembers(@RequestParam("page") int page , @PathVariable String groupId){
        return getAllMembers.getAllMembers(groupId , page) ;
    }

    @GetMapping(value = "/get/member/{groupId}")
    public MemberDto getThisMember(@PathVariable String groupId) {
        return getMembers.getThisMember(groupId) ;
    }
    @GetMapping(value = "/get/suggestions/{groupId}")
    public List<String> getSuggestions(@PathVariable String groupId , @RequestParam("value") String value){
        return getSuggestions.getSuggestions(groupId , value) ;
    }
    @DeleteMapping(value = "/member/delete/{username}/{groupId}")
    public void deleteMember(@PathVariable String username , @PathVariable String groupId){
        removeMember.removeMember(username , groupId);
    }
    @DeleteMapping(value = "/delete/all/user/memberships")
    public void removeAllUserMemberships(){
        removeMember.removeAllUserMemberships();
    }



}

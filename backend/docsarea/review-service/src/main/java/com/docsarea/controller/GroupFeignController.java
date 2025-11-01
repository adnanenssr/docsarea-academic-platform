package com.docsarea.controller;

import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "group-service")
public interface GroupFeignController {
    @GetMapping(value = "/get/members/{role}/{groupId}")
    public List<String> getGroupModerators(@PathVariable String groupId , @PathVariable GroupRoles role) ;
    @GetMapping(value = "/get/member/{groupId}/{username}")
    public MemberDto getMember(@PathVariable String groupId , @PathVariable String username) ;

}

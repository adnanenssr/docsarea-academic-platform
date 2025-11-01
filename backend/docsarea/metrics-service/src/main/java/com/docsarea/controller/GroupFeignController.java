package com.docsarea.controller;

import com.docsarea.dtos.member.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("group-service")
public interface GroupFeignController {
    @GetMapping(value = "/get/member/{groupId}/{username}")
    MemberDto getMember(@PathVariable String groupId, @PathVariable String username);

}

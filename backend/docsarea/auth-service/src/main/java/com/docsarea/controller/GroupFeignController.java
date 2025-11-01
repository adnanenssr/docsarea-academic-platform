package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

@FeignClient("group-service")
public interface GroupFeignController {
    @DeleteMapping(value = "/delete/all/user/memberships")
    public void removeAllUserMemberships() ;
    @DeleteMapping(value = "/delete/all/user/groups")
    public void deleteAllUserGroups() ;


}

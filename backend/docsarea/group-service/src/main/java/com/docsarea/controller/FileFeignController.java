package com.docsarea.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("file-service")
public interface FileFeignController {

    @PutMapping(value = "/transfer/{username}/{groupId}")
    public void transferFilesToUser(@PathVariable String username , @PathVariable String groupId);
    @DeleteMapping(value = "/file/user/group/delete/{username}/{groupId}")
    public void deleteUserGroupFiles(@PathVariable String username , @PathVariable String groupId);

}

package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUsedSpace {

    @Autowired
    FileRepo fileRepo ;
    @Autowired
    GroupFeignController groupFeignController ;

    public String userUsedSpace(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Long storage =  fileRepo.getUserUsedSpace(user) ;
        if(storage == null) storage = 0L ;
        return formatStorage(storage) ;
    }


    public boolean isAdmin(String groupId){
        String username = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = groupFeignController.getMember(groupId , username) ;
        return member.getRole().isHigherThan(GroupRoles.MODERATOR) ;
    }

    public String groupUsedSpace(String groupId){
        if(!isAdmin(groupId)) return "?" ;
        isAdmin(groupId);
        Long storage = fileRepo.getGroupUsedSpace(groupId) ;
        if(storage == null) storage = 0L ;
        return formatStorage(storage) ;
    }

    public static String formatStorage(Long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }

        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return formatDecimal(kb) + " KB";
        }

        double mb = kb / 1024.0;
        if (mb < 1024) {
            return formatDecimal(mb) + " MB";
        }

        double gb = mb / 1024.0;
        if (gb < 1024) {
            return formatDecimal(gb) + " GB";
        }

        double tb = gb / 1024.0;
        return formatDecimal(tb) + " TB";
    }

    private static String formatDecimal(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.format("%.1f", value);
    }

}

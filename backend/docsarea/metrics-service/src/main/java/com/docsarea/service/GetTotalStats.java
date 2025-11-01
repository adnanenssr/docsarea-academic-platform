package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.repository.DownloadsRepo;
import com.docsarea.repository.ViewsRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GetTotalStats {
    @Autowired
    ViewsRepo viewsRepo ;
    @Autowired
    DownloadsRepo downloadsRepo ;
    @Autowired
    GroupFeignController groupFeignController ;

    public List<String> groupTotalMetrics(String publisher){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = isMember(user , publisher) ;
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return getGroupTotalMetrics(publisher) ;
        return getMemberTotalMetrics(publisher , user) ;
    }

    public List<String> getGroupTotalMetrics(String publisher){
        Long views = viewsRepo.getGroupUserTotalViews(publisher).orElse(0L) ;
        Long downloads = downloadsRepo.getGroupUserTotalDownloads(publisher).orElse(0L) ;
        return Arrays.asList(formatNumber(views) , formatNumber(downloads)) ;
    }

    public List<String> userTotalMetrics(){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Long views = viewsRepo.getGroupUserTotalViews(user).orElse(0L) ;
        Long downloads = downloadsRepo.getGroupUserTotalDownloads(user).orElse(0L) ;
        return Arrays.asList(formatNumber(views) , formatNumber(downloads)) ;
    }

    public List<String> getMemberTotalMetrics(String publisher , String user){
        Long views = viewsRepo.getMemberTotalViews(publisher , user).orElse(0L) ;
        Long downloads = downloadsRepo.getMemberTotalDownloads(publisher , user).orElse(0L) ;
        return Arrays.asList(formatNumber(views) , formatNumber(downloads)) ;
    }

    public MemberDto isMember(String username  , String publisher){
        return groupFeignController.getMember(publisher , username) ;
    }




    public static String formatNumber(long num) {
        if (num >= 1_000_000_000) {
            return formatDecimal(num / 1_000_000_000.0) + "B";
        }
        if (num >= 1_000_000) {
            return formatDecimal(num / 1_000_000.0) + "M";
        }
        if (num >= 1_000) {
            return formatDecimal(num / 1_000.0) + "K";
        }
        return String.valueOf(num);
    }

    private static String formatDecimal(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.format("%.1f", value);
    }

}

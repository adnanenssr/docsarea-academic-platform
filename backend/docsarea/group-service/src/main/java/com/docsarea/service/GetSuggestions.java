package com.docsarea.service;

import com.docsarea.enums.GroupRoles;
import com.docsarea.model.Member;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSuggestions {
    @Autowired
    MemberRepo memberRepo ;

    public List<String> getSuggestions (String groupId , String value){
        memberRepo.findByGroupIdAndUsername(groupId , CurrentAuthenticatedUser.getCurrentAuthenticatedUser()).orElseThrow(() -> new RuntimeException("you are not a member")) ;
        PageRequest pageable = PageRequest.of(0,10) ;
        return memberRepo.findByUsernameStartingWithAndGroupIdAndRole(value , groupId , GroupRoles.ADMIN , pageable).stream().map(Member::getUsername).toList() ;
    }
}

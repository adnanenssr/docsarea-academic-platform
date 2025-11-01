package com.docsarea.service;

import com.docsarea.dtos.invitation.GetInvitationLink;
import com.docsarea.dtos.invitation.UpdateLinkDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.InvitationLink;
import com.docsarea.model.Member;
import com.docsarea.repository.InvitationLinkRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateLinkService {

    @Autowired
    InvitationLinkRepo linkRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    MemberRepo memberRepo ;

    public void verifyAdmin(String groupId){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        Member member = memberRepo.findByGroupIdAndUsername(groupId , user).orElseThrow(() -> new RuntimeException("you are not member of this group") ) ;
        if(!member.getRole().isHigherThan(GroupRoles.MODERATOR)) throw new RuntimeException("you can't access this resource");

    }

    public void verifyExpiration(LocalDateTime expiresAt){
        if(expiresAt.isBefore(LocalDateTime.now())) throw new RuntimeException("please set a valid expiration time") ;
    }

    public InvitationLink getLink(String id){
        return linkRepo.findById(id).orElseThrow(() -> new RuntimeException("this link does not exist")) ;

    }

    public GetInvitationLink updateLink(String id , UpdateLinkDto dto ){

        verifyExpiration(dto.getExpiresAt());
        InvitationLink link = getLink(id) ;
        verifyAdmin(link.getGroupId());
        link.setActive(dto.isActive());
        link.setExpiresAt(dto.getExpiresAt());
        link.setDescription(dto.getDescription());
        InvitationLink updated = linkRepo.save(link) ;
        return modelMapper.map(updated , GetInvitationLink.class) ;
    }
}

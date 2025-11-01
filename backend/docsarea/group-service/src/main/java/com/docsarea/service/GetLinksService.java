package com.docsarea.service;

import com.docsarea.dtos.invitation.GetInvitationLink;
import com.docsarea.dtos.invitation.GetLinkPageDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.InvitationLink;
import com.docsarea.model.Member;
import com.docsarea.repository.InvitationLinkRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetLinksService {
    @Autowired
    MemberRepo memberRepo ;
    @Autowired
    InvitationLinkRepo linkRepo ;

    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public GroupRoles verifyAdmin(String user , String groupId){
        Member member = memberRepo.findByGroupIdAndUsername(groupId , user).orElseThrow(() -> new RuntimeException("you don't have permission to see this page")) ;
        if(!member.getRole().isHigherThan(GroupRoles.MODERATOR)) throw new RuntimeException("you don't have permission to see this page") ;
        if(!member.isInvitePermission()) throw new RuntimeException("you don't have permission to see this page") ;
        return member.getRole() ;
    }

    public Page<InvitationLink> getList(GroupRoles role , String groupId , String username , Pageable page){

        Page<InvitationLink> links ;

        if(role == GroupRoles.ADMIN ) {
            links = linkRepo.findByGroupIdAndCreatedByOrderByCreatedAtDesc(groupId , username , page) ;
        }else{
            links = linkRepo.findByGroupIdOrderByCreatedAtDesc(groupId , page) ;
        }

        return links ;

    }

    public GetLinkPageDto getLinks(String groupId , Integer page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        GroupRoles role = verifyAdmin(user , groupId) ;
        Pageable pageable = PageRequest.of(page , 10) ;
        Page<InvitationLink> links = getList(role , groupId , user , pageable) ;
        List<GetInvitationLink> dto =  links.stream().map(link -> modelMapper.map(link , GetInvitationLink.class)).toList();
        GetLinkPageDto pageDto = new GetLinkPageDto() ;
        pageDto.setLinks(dto);
        pageDto.setNumPages(links.getTotalPages());
        pageDto.setNumElements(links.getTotalElements());
        return pageDto ;
    }

}

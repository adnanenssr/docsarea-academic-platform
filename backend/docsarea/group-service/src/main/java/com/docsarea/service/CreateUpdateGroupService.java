package com.docsarea.service;

import com.docsarea.controller.StorageFeignController;
import com.docsarea.dtos.group.CreateGroupDto;
import com.docsarea.dtos.group.GetGroupDto;
import com.docsarea.dtos.group.UpdateGroupDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.FileUploadPermission;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.model.Group;
import com.docsarea.model.Member;
import com.docsarea.repository.GroupRepo;
import com.docsarea.repository.MemberRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CreateUpdateGroupService {

    @Autowired
    GroupRepo groupRepo;
    ModelMapper modelMapper = DefaultMapper.getModelMapper();
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    StorageFeignController storageFeignController ;

    public GetGroupDto saveGroup(CreateGroupDto dto) {
        Group group = modelMapper.map(dto, Group.class);
        group.setOwner(CurrentAuthenticatedUser.getCurrentAuthenticatedUser());
        Group saved = groupRepo.save(group);
        addGroupOwner(saved.getId());

        return modelMapper.map(saved, GetGroupDto.class);

    }

    public void isOwner(String groupId) {
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser();
        Member member = memberRepo.findByGroupIdAndUsername(groupId, user).orElseThrow(() -> new RuntimeException("this member does not exist"));
        if (!member.getRole().isHigherThan(GroupRoles.ADMIN))
            throw new RuntimeException("you don't have permission to update this group's details");
    }

    public GetGroupDto updateGroup(UpdateGroupDto dto, String id) {
        isOwner(id);
        Group group = groupRepo.findById(id).orElseThrow(() -> new RuntimeException("this group does not exist"));

        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setTheme(dto.getTheme());
        group.setPrivacy(dto.getPrivacy());
        group.setJoinPolicy(dto.getJoinPolicy());
        Group updated = groupRepo.save(group);
        return modelMapper.map(updated, GetGroupDto.class);
    }

    public void updateAvatar (MultipartFile file ,String groupId) throws IOException {
        isOwner(groupId);
        String path = storageFeignController.saveGroupAvatar(file , groupId) ;
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        group.setProfileImg(path);
        groupRepo.save(group) ;
    }

    public void updateCover (MultipartFile file , String groupId) throws IOException {
        isOwner(groupId);
        String path = storageFeignController.saveGroupCover(file , groupId);
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        group.setCoverImg(path);
        groupRepo.save(group) ;
    }

    public void deleteCover (String groupId) {
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        group.setCoverImg("");
    }

    public void deleteAvatar (String groupId) {
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new RuntimeException("this group does not exist")) ;
        group.setProfileImg("");
    }

    public void addGroupOwner(String groupId) {

        MemberDto owner = new MemberDto(
                CurrentAuthenticatedUser.getCurrentAuthenticatedUser(),
                GroupRoles.OWNER,
                true,
                true,
                true,
                FileUploadPermission.AUTO_PUBLISH
        );
        Member member = modelMapper.map(owner, Member.class);
        member.setGroupId(groupId);
        memberRepo.save(member);
    }

    public void transferOwnership(String groupId, String newOwner) {

        isOwner(groupId);
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser();
        Member oldOwner = memberRepo.findByGroupIdAndUsername(groupId, user).orElseThrow(() -> new RuntimeException("this member does not exist"));
        Member member = memberRepo.findByGroupIdAndUsername(groupId, newOwner).orElseThrow(() -> new RuntimeException("this member does not exist"));
        if (member.getRole() != GroupRoles.ADMIN)
            throw new RuntimeException("the member you are trying to transfer ownership to , is not an Admin of this group");
        member.setRole(GroupRoles.OWNER);
        oldOwner.setRole(GroupRoles.ADMIN);
        Member[] array = {member, oldOwner};
        List<Member> members = Arrays.asList(array);
        memberRepo.saveAll(members);

    }
}
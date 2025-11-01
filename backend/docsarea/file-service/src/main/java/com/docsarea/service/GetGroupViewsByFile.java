package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.controller.MetricsFeignController;
import com.docsarea.dtos.file.FileMetricDto;
import com.docsarea.dtos.file.FileMetricPageDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.views.ViewsPageDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.mappers.DefaultMapper;
import com.docsarea.module.Document;
import com.docsarea.repository.FileRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetGroupViewsByFile {
    @Autowired
    MetricsFeignController metricsFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;
    @Autowired
    GroupFeignController groupFeignController ;

    public FileMetricPageDto getGroupViewsByFile(String groupId , LocalDate start , LocalDate end , int page){
        ViewsPageDto views = metricsFeignController.getTotalViewsPerFile(groupId ,start, end, page) ;
        if(views == null || views.getViews() == null || views.getViews().isEmpty()) {
            return new FileMetricPageDto(List.of(), 0, 0L);
        }
        List<Document> documents = fileRepo.findAllById(views.getViews().keySet()) ;
        Map<String , Document > map = documents.stream().collect(Collectors.toMap(Document::getId , doc -> doc )) ;
        List<FileMetricDto> files = views.getViews().keySet().stream().map((fileId) -> {
                    Document file = map.get(fileId) ;
                    if(file == null) return null ;
                    FileMetricDto dto = modelMapper.map(file , FileMetricDto.class) ;
                    dto.setMetric(views.getViews().get(fileId));
                    return dto ;
                })
                .filter(Objects::nonNull)
                .toList() ;
        return new FileMetricPageDto(files , views.getNumPages(), views.getNumElements()) ;
    }

    public MemberDto isMember(String groupId ,String user ){
        return groupFeignController.getMember(groupId , user) ;
    }

    public FileMetricPageDto getMemberViewsByFile(String groupId , String user , LocalDate start , LocalDate end , int page){
        ViewsPageDto views = metricsFeignController.getMemberTotalViewsPerFile(groupId, user ,start, end, page) ;
        if(views == null || views.getViews() == null || views.getViews().isEmpty()) {
            return new FileMetricPageDto(List.of(), 0, 0L);
        }
        List<Document> documents = fileRepo.findAllById(views.getViews().keySet()) ;
        Map<String , Document > map = documents.stream().collect(Collectors.toMap(Document::getId , doc -> doc )) ;
        List<FileMetricDto> files = views.getViews().keySet().stream().map((fileId) -> {
                    Document file = map.get(fileId) ;
                    if(file == null) return null ;
                    FileMetricDto dto = modelMapper.map(file , FileMetricDto.class) ;
                    dto.setMetric(views.getViews().get(fileId));
                    return dto ;
                })
                .filter(Objects::nonNull)
                .toList() ;
        return new FileMetricPageDto(files , views.getNumPages(), views.getNumElements()) ;
    }

    public FileMetricPageDto getViewsByFile(String groupId , LocalDate start , LocalDate end , int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = isMember(groupId , user) ;
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return getGroupViewsByFile(groupId , start , end , page) ;
        return getMemberViewsByFile(groupId , user , start , end , page) ;
    }

}

package com.docsarea.service;

import com.docsarea.controller.MetricsFeignController;
import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.dtos.file.FileMetricDto;
import com.docsarea.dtos.file.FileMetricPageDto;
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
public class GetUserDownloadsByFile {
    @Autowired
    MetricsFeignController metricsFeignController ;
    @Autowired
    FileRepo fileRepo ;
    ModelMapper modelMapper = DefaultMapper.getModelMapper() ;

    public FileMetricPageDto getDownloadsByFile(LocalDate start , LocalDate end , int page){
        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        DownloadsPageDto downloads = metricsFeignController.getTotalDownloadsPerFile(user ,start, end, page) ;
        if(downloads == null || downloads.getDownloads() == null || downloads.getDownloads().isEmpty()) {
            return new FileMetricPageDto(List.of(), 0, 0L);
        }
        List<Document> documents = fileRepo.findAllById(downloads.getDownloads().keySet()) ;
        Map<String , Document > map = documents.stream().collect(Collectors.toMap(Document::getId , doc -> doc )) ;
        List<FileMetricDto> files = downloads.getDownloads().keySet().stream().map((fileId) -> {
                    Document file = map.get(fileId) ;
                    if(file == null) return null ;
                    FileMetricDto dto = modelMapper.map(file , FileMetricDto.class) ;
                    dto.setMetric(downloads.getDownloads().get(fileId));
                    return dto ;
                })
                .filter(Objects::nonNull)
                .toList() ;
        return new FileMetricPageDto(files , downloads.getNumPages(), downloads.getNumElements()) ;
    }

}

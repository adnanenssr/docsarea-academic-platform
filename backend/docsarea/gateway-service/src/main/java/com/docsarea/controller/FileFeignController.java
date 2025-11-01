package com.docsarea.controller;


import com.docsarea.configuration.FeignConfig;
import com.docsarea.configuration.SecurityConfig;
import com.docsarea.dtos.file.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "file-service" , configuration = FeignConfig.class)
public interface FileFeignController {

    @PostMapping(value = "/file/user/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetFileDto userUpload(
            @RequestPart("file") MultipartFile file,
            @RequestParam("data") String data);

    @PostMapping(value = "file/group/{groupId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetFileDto groupUpload(@RequestPart("file") MultipartFile file, @RequestParam("data") String data, @PathVariable String groupId);

    @PutMapping(value = "/file/update/file/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetFileDto updateFile(@RequestPart("file") MultipartFile file, @PathVariable String id);

    @PutMapping(value = "/file/update/details/{id}")
    public GetFileDto updateFileDetails(@RequestBody FileUploadDto dto, @PathVariable String id);

    @GetMapping(value = "/file/inline/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id);

    @GetMapping(value = "/file/details/{id}")
    public GetFileDto getFileDetails(@PathVariable String id);

    @GetMapping(value = "/file/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id);

    @GetMapping(value = "/file/page")
    public FilePageDto getUserPage(@RequestParam("page") int page);

    @GetMapping(value = "/{groupId}/file/published")
    public FilePageDto getPublished(@PathVariable String groupId, @RequestParam("page") int page);

    @GetMapping(value = "/{groupId}/file/review")
    public FilePageDto getInReview(@PathVariable String groupId, @RequestParam("page") int page);

    @GetMapping(value = "/{groupId}/file/rejected")
    public FilePageDto getRejected(@PathVariable String groupId, @RequestParam("page") int page);

    @GetMapping(value = "/{groupId}/review/published")
    public FileModeratorPageDto getReviewPublished(@RequestParam("page") int page , @PathVariable String groupId) ;

    @GetMapping(value = "/{groupId}/review/review")
    public FileModeratorPageDto getReviewInReview(@RequestParam("page") int page , @PathVariable String groupId);

    @GetMapping(value = "/{groupId}/review/rejected")
    public FileModeratorPageDto getReviewRejected(@RequestParam("page") int page , @PathVariable String groupId);

    @GetMapping(value = "/get/views/files")
    public FileMetricPageDto getUserViewsByFile(@RequestParam("start") LocalDate start ,
                                                @RequestParam("end") LocalDate end ,
                                                @RequestParam("page") int page
    );

    @GetMapping(value = "/get/views/files/{groupId}")
    public FileMetricPageDto getGroupViewsByFile(@RequestParam("start") LocalDate start ,
                                                 @RequestParam("end") LocalDate end ,
                                                 @RequestParam("page") int page ,
                                                 @PathVariable String groupId
    );

    @GetMapping(value = "/get/downloads/files")
    public FileMetricPageDto getUserDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                    @RequestParam("end") LocalDate end ,
                                                    @RequestParam("page") int page
    );

    @GetMapping(value = "/get/downloads/files/{groupId}")
    public FileMetricPageDto getGroupDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                     @RequestParam("end") LocalDate end ,
                                                     @RequestParam("page") int page ,
                                                     @PathVariable String groupId
    );

    @GetMapping(value = "/get/group/{groupId}/files/published")
    public FilePageDto getGroupPublished(@PathVariable String groupId , @RequestParam("page") int page);

    @GetMapping(value = "/get/user/{username}/files/published")
    public FilePageDto getUserPublished(@PathVariable String username , @RequestParam("page") int page);

    @GetMapping(value = "/get/user/stats")
    public List<String> userStats();
    @GetMapping(value = "/get/group/stats/{groupId}")
    public List<String> groupStats(@PathVariable String groupId);

    @GetMapping(value = "/get/group/stats/{groupId}")
    public long memberStats(@PathVariable String groupId);

    @GetMapping(value = "/get/downloads/files/member/{groupId}")
    public FileMetricPageDto getMemberDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                      @RequestParam("end") LocalDate end ,
                                                      @RequestParam("page") int page ,
                                                      @PathVariable String groupId
    );

    @GetMapping(value = "/get/views/files/member/{groupId}")
    public FileMetricPageDto getMemberViewsByFile(@RequestParam("start") LocalDate start ,
                                                       @RequestParam("end") LocalDate end ,
                                                       @RequestParam("page") int page ,
                                                       @PathVariable String groupId
    );

    @GetMapping(value = "/get/recent/user")
    public List<GetFileDto> getUserRecentModified();
    @GetMapping(value = "/get/recent/group/{groupId}")
    public List<GetFileDto> getGroupRecentModified(@PathVariable String groupId);
    @GetMapping(value = "/get/downloads")
    public FilePageDto getDownloads(@RequestParam int page);

    @GetMapping(value = "/get/bookmarks")
    public FilePageDto getBookmarks(@RequestParam int page);

    @GetMapping(value = "/search")
    public FilePageDto search(@RequestParam("q") String query , @RequestParam("page") int page);


    @GetMapping(value = "/migrate")
    public void migrate ();

    @GetMapping(value = "/thumbnail/{fileId}")
    public ResponseEntity<Resource> getThumbnail (@PathVariable String fileId) ;

    @GetMapping(value = "/get/recommendations")
    public List<GetFileDto> getRecommendations(@RequestParam("q") String query , @RequestParam("fileId") String fileId);

    @DeleteMapping(value = "/file/delete/{fileId}")
    public void userFileDelete(@PathVariable String fileId);
    @DeleteMapping(value = "/file/delete/{groupId}/{fileId}")
    public void groupFileDelete(@PathVariable String groupId ,@PathVariable String fileId);


}


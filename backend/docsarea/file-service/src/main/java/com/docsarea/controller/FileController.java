package com.docsarea.controller;

import com.docsarea.dtos.file.*;
import com.docsarea.enums.Status;
import com.docsarea.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
public class FileController {
    @Autowired
    UserFileUploadService userUploadService ;
    @Autowired
    ReturnFileService returnFileService ;
    @Autowired
    GetRecentModified getRecentModified ;

    @Autowired
    UpdateFile updateFile ;
    @Autowired
    GroupFileUploadService groupUploadService ;
    @Autowired
    UserFilePageService userFilePageService ;
    @Autowired
    GetGroupMemberFiles getGroupMemberFiles ;
    @Autowired
    GetReviews getReviews ;
    @Autowired
    GetUserViewsByFile getUserViewsByFile ;
    @Autowired
    GetGroupViewsByFile getGroupViewsByFile ;
    @Autowired
    GetUserDownloadsByFile getUserDownloadsByFile ;
    @Autowired
    GetGroupDownloadsByFile getGroupDownloadsByFile ;
    @Autowired
    GroupPublished groupPublished ;
    @Autowired
    UserPublished userPublished ;
    @Autowired
    GetStats getStats ;
    @Autowired
    SizeToStorage sizeToStorage ;
    @Autowired
    GetUserDownloadedFiles getUserDownloadedFiles ;

    @Autowired
    GetUserBookmarks getUserBookmarks ;
    @Autowired
    SearchService searchService ;

    @Autowired
    GetThumbnail getThumbnail ;
    @Autowired
    DeleteFile deleteFile ;
    @Autowired
    TransferFilesToUser transferFiles ;
    @Autowired
    DeleteUserGroupFiles deleteUserGroup ;
    @Autowired
    HandleUserDelete handleUserDelete;


    @PostMapping(value = "file/user/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "application/json")
    public ResponseEntity<GetFileDto>  userUpload (@RequestPart("file") MultipartFile file  , @RequestParam("data") String data  ) throws JsonProcessingException {
        System.out.println(data);
        ObjectMapper objectMapper = new ObjectMapper() ;
        FileUploadDto userDto = objectMapper.readValue(data , FileUploadDto.class) ;
        System.out.println("here is fine");

        GetFileDto dto = userUploadService.userUpload(userDto , file ) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @PostMapping(value = "file/group/{groupId}/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "Application/json")
    public ResponseEntity<GetFileDto>  groupUpload (  @RequestPart("file") MultipartFile file , @RequestParam("data") String data , @PathVariable String groupId ) throws JsonProcessingException {
        System.out.println(data);
        ObjectMapper objectMapper = new ObjectMapper() ;
        FileUploadDto groupDto = objectMapper.readValue(data , FileUploadDto.class) ;
        System.out.println("here is fine");
        GetFileDto dto = groupUploadService.groupUpload(groupDto , file , groupId) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }


    @PutMapping(value = "/file/update/details/{id}")
    public ResponseEntity<GetFileDto> updateDetails(@RequestBody FileUploadDto data , @PathVariable String id ){
        GetFileDto dto =  updateFile.updateDetails(data , id) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @PutMapping(value = "/file/update/file/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetFileDto> updateFile(@RequestPart("file") MultipartFile file , @PathVariable String id ){
        GetFileDto dto = updateFile.updateFile(file , id );
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @GetMapping(value = "/file/inline/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id ) throws IOException {

        Resource resource = returnFileService.getFile(id);
        GetFileDto dto = returnFileService.getDetails(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION , "inline; filename=\"" + dto.getTitle() + dto.getExtension() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource) ;


    }

    @GetMapping(value = "/file/details/{id}")
    public GetFileDto getDetails(@PathVariable String id) {
        return returnFileService.getDetails(id);
    }


    @GetMapping(value = "/file/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id ) throws IOException {

        Resource resource = returnFileService.downloadFile(id);
        GetFileDto dto = returnFileService.getDetails(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" + dto.getTitle() + dto.getExtension() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource) ;


    }

    @GetMapping(value = "/file/page")
    public FilePageDto getFilePage(@RequestParam("page") int page) {
        return userFilePageService.getUserPage(page) ;
    }

    @GetMapping(value = "/{groupId}/file/published")
    public FilePageDto getPublished(@PathVariable String groupId , @RequestParam("page") int page){
        return getGroupMemberFiles.getPublished(groupId , page);
    }
    @GetMapping(value = "/{groupId}/file/review")
    public FilePageDto getInReview(@PathVariable String groupId , @RequestParam("page") int page){
        return getGroupMemberFiles.getInReview(groupId , page);
    }
    @GetMapping(value = "/{groupId}/file/rejected")
    public FilePageDto getRejected(@PathVariable String groupId , @RequestParam("page") int page){
        return getGroupMemberFiles.getRejected(groupId , page);
    }

    @GetMapping(value = "/{groupId}/review/published")
    public FileModeratorPageDto getReviewPublished(@RequestParam("page") int page , @PathVariable String groupId){
        return getReviews.getPublished(groupId , page ) ;
    }

    @GetMapping(value = "/{groupId}/review/review")
    public FileModeratorPageDto getReviewInReview(@RequestParam("page") int page , @PathVariable String groupId){
        return getReviews.getInReview(groupId , page ) ;
    }

    @GetMapping(value = "/{groupId}/review/rejected")
    public FileModeratorPageDto getReviewRejected(@RequestParam("page") int page , @PathVariable String groupId){
        return getReviews.getRejected(groupId , page ) ;
    }

    @GetMapping(value = "/get/views/files")
    public FileMetricPageDto getUserViewsByFile(@RequestParam("start") LocalDate start ,
                                                @RequestParam("end") LocalDate end ,
                                                @RequestParam("page") int page
    ){
        return getUserViewsByFile.getViewsByFile(start , end , page) ;
    }

    @GetMapping(value = "/get/views/files/{groupId}")
    public FileMetricPageDto getGroupViewsByFile(@RequestParam("start") LocalDate start ,
                                                 @RequestParam("end") LocalDate end ,
                                                 @RequestParam("page") int page ,
                                                 @PathVariable String groupId
    ){
        return getGroupViewsByFile.getViewsByFile(groupId , start , end , page) ;
    }

    @GetMapping(value = "/get/downloads/files")
    public FileMetricPageDto getUserDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                @RequestParam("end") LocalDate end ,
                                                @RequestParam("page") int page
    ){
        return getUserDownloadsByFile.getDownloadsByFile(start , end , page) ;
    }

    @GetMapping(value = "/get/downloads/files/{groupId}")
    public FileMetricPageDto getGroupDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                 @RequestParam("end") LocalDate end ,
                                                 @RequestParam("page") int page ,
                                                 @PathVariable String groupId
    ){
        return getGroupDownloadsByFile.getDownloadsByFile(groupId , start , end , page) ;
    }

    @GetMapping(value = "/get/group/{groupId}/files/published")
    public FilePageDto getGroupPublished(@PathVariable String groupId , @RequestParam("page") int page){
        return groupPublished.getGroupPublished(groupId , page) ;
    }

    @GetMapping(value = "/get/user/{username}/files/published")
    public FilePageDto getUserPublished(@PathVariable String username , @RequestParam("page") int page){
        return userPublished.getUserPublished(username , page) ;
    }

    @PutMapping(value = "/update/file/status/{fileId}")
    public void updateStatus (@PathVariable String fileId , @RequestParam("status") Status status ){
        updateFile.updateStatus(fileId , status );
    }

    @GetMapping(value = "/get/user/stats")
    public List<String> userStats(){
        return getStats.getUserStats() ;
    }
    @GetMapping(value = "/get/group/stats/{groupId}")
    public List<String> groupStats(@PathVariable String groupId){
        return getStats.getGroupStats(groupId) ;
    }

    @GetMapping(value = "/get/recent/user")
    public List<GetFileDto> getUserRecentModified(){
        return getRecentModified.userRecentModified() ;
    }
    @GetMapping(value = "/get/recent/group/{groupId}")
    public List<GetFileDto> getGroupRecentModified(@PathVariable String groupId){
        return getRecentModified.groupRecentModified(groupId) ;
    }


    @GetMapping(value = "/get/downloads")
    public FilePageDto getDownloads(@RequestParam("page") int page){
        return getUserDownloadedFiles.getDownloads(page) ;
    }

    @GetMapping(value = "/get/bookmarks")
    public FilePageDto getBookmarks(@RequestParam("page") int page){
        return getUserBookmarks.getBookmarks(page) ;
    }

    @GetMapping(value = "/search")
    public FilePageDto search(@RequestParam("q") String query , @RequestParam("page") int page){
        return searchService.search(query , page) ;
    }


    @GetMapping(value = "/migrate")
    public void migrate (){
        searchService.migrate();
    }

    @GetMapping(value = "/thumbnail/{fileId}")
    public ResponseEntity<Resource> getThumbnail (@PathVariable String fileId){
        Resource resource = getThumbnail.getThumbnail(fileId) ;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE , "image/jpg")
                .body(resource) ;
    }

    @GetMapping(value = "/get/recommendations")
    public List<GetFileDto> getRecommendations(@RequestParam("q") String query , @RequestParam("fileId") String fileId){
        return searchService.getRecommendations(query , fileId) ;
    }

    @DeleteMapping(value = "/file/delete/{fileId}")
    public void UserFileDelete(@PathVariable String fileId){
        deleteFile.userFileDelete(fileId);
    }
    @DeleteMapping(value = "/file/delete/{groupId}/{fileId}")
    public void GroupFileDelete(@PathVariable String groupId ,@PathVariable String fileId){
        deleteFile.groupFileDelete(fileId , groupId);
    }

    @PutMapping(value = "/transfer/{username}/{groupId}")
    public void transferFilesToUser(@PathVariable String username , @PathVariable String groupId){
        transferFiles.transferFilesToUser(username , groupId);
    }
    @DeleteMapping(value = "/file/user/group/delete/{username}/{groupId}")
    public void deleteUserGroupFiles(@PathVariable String username , @PathVariable String groupId){
        deleteUserGroup.deleteUserGroupFiles(username , groupId);
    }

    @GetMapping(value = "/have/group/file")
    public boolean handleUserDelete(){
        return handleUserDelete.doesHaveGroupFiles() ;
    }














}

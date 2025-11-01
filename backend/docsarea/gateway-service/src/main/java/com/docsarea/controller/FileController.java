package com.docsarea.controller;

import com.docsarea.dtos.file.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileFeignController fileFeignController ;


    @PostMapping (value = "/upload" , consumes= MediaType.MULTIPART_FORM_DATA_VALUE , produces = "application/json")
    public ResponseEntity<GetFileDto> upload (@RequestPart("file") MultipartFile file , @RequestPart("data") UserFileUploadDto data) throws JsonProcessingException {
        System.out.println("i am here inside the /upload ");
        ObjectMapper objectMapper = new ObjectMapper() ;
        String dataJson = objectMapper.writeValueAsString(data) ;
        GetFileDto dto =  fileFeignController.userUpload(file , dataJson) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @PostMapping (value = "/upload/{groupId}" , consumes= MediaType.MULTIPART_FORM_DATA_VALUE , produces = "application/json")
    public ResponseEntity<GetFileDto> groupUpload (@RequestPart("file") MultipartFile file , @RequestPart("data") UserFileUploadDto data , @PathVariable String groupId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper() ;
        String dataJson = objectMapper.writeValueAsString(data) ;
        GetFileDto dto =  fileFeignController.groupUpload(file , dataJson , groupId) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }



    @PutMapping(value = "/file/file/update/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetFileDto> updateFile (@RequestPart("file") MultipartFile file , @PathVariable String id ){
        GetFileDto dto = fileFeignController.updateFile(file , id) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @PutMapping(value = "/file/details/update/{id}")
    public ResponseEntity<GetFileDto> updateFileDetails(@RequestBody FileUploadDto dto , @PathVariable String id){
        GetFileDto result = fileFeignController.updateFileDetails(dto , id) ;
        return new ResponseEntity<>(result , HttpStatus.OK) ;
    }

    @GetMapping(value = "/file/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id ){
        return fileFeignController.getFile(id) ;
    }

    @GetMapping(value = "/file/details/{id}")
    public ResponseEntity<GetFileDto> getFileDetails(@PathVariable String id){
        GetFileDto dto = fileFeignController.getFileDetails(id) ;
        return new ResponseEntity<>(dto , HttpStatus.OK) ;
    }

    @GetMapping(value ="/file/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id){
        return fileFeignController.downloadFile(id) ;
    }

    @GetMapping(value = "/file/page")
    public FilePageDto getFilePage(@RequestParam("page") int page) {
        return fileFeignController.getUserPage(page) ;
    }

    @GetMapping(value = "/{groupId}/file/published")
    public FilePageDto getPublished(@PathVariable String groupId , @RequestParam("page") int page){
        return fileFeignController.getPublished(groupId , page);
    }
    @GetMapping(value = "/{groupId}/file/review")
    public FilePageDto getInReview(@PathVariable String groupId , @RequestParam("page") int page){
        return fileFeignController.getInReview(groupId , page);
    }
    @GetMapping(value = "/{groupId}/file/rejected")
    public FilePageDto getRejected(@PathVariable String groupId , @RequestParam("page") int page){
        return fileFeignController.getRejected(groupId , page);
    }

    @GetMapping(value = "/{groupId}/review/published")
    public FileModeratorPageDto getReviewPublished(@RequestParam("page") int page , @PathVariable String groupId){
        return fileFeignController.getReviewPublished( page, groupId ) ;
    }

    @GetMapping(value = "/{groupId}/review/review")
    public FileModeratorPageDto getReviewInReview(@RequestParam("page") int page , @PathVariable String groupId){
        return fileFeignController.getReviewInReview( page, groupId ) ;
    }

    @GetMapping(value = "/{groupId}/review/rejected")
    public FileModeratorPageDto getReviewRejected(@RequestParam("page") int page , @PathVariable String groupId){
        return fileFeignController.getReviewRejected( page, groupId ) ;
    }

    @GetMapping(value = "/get/views/files")
    public FileMetricPageDto getUserViewsByFile(@RequestParam("start") LocalDate start ,
                                                @RequestParam("end") LocalDate end ,
                                                @RequestParam("page") int page
    ){
        return fileFeignController.getUserViewsByFile(start , end , page) ;
    }

    @GetMapping(value = "/get/views/files/{groupId}")
    public FileMetricPageDto getGroupViewsByFile(@RequestParam("start") LocalDate start ,
                                                 @RequestParam("end") LocalDate end ,
                                                 @RequestParam("page") int page ,
                                                 @PathVariable String groupId
    ){
        return fileFeignController.getGroupViewsByFile(start , end , page , groupId) ;
    }

    @GetMapping(value = "/get/downloads/files")
    public FileMetricPageDto getUserDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                    @RequestParam("end") LocalDate end ,
                                                    @RequestParam("page") int page
    ){
        return fileFeignController.getUserDownloadsByFile(start , end , page) ;
    }

    @GetMapping(value = "/get/downloads/files/{groupId}")
    public FileMetricPageDto getGroupDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                     @RequestParam("end") LocalDate end ,
                                                     @RequestParam("page") int page ,
                                                     @PathVariable String groupId
    ){
        return fileFeignController.getGroupDownloadsByFile(start , end , page , groupId) ;
    }

    @GetMapping(value = "/get/group/{groupId}/files/published")
    public FilePageDto getGroupPublished(@PathVariable String groupId , @RequestParam("page") int page){
        return fileFeignController.getGroupPublished(groupId , page) ;
    }

    @GetMapping(value = "/get/user/{username}/files/published")
    public FilePageDto getUserPublished(@PathVariable String username , @RequestParam("page") int page){
        return fileFeignController.getUserPublished(username , page) ;
    }

    @GetMapping(value = "/get/user/stats")
    public List<String> userStats(){
        return fileFeignController.userStats() ;
    }
    @GetMapping(value = "/get/group/stats/{groupId}")
    public List<String> groupStats(@PathVariable String groupId){
        return fileFeignController.groupStats(groupId) ;
    }


    @GetMapping(value = "/get/recent/user")
    public List<GetFileDto> getUserRecentModified(){
        return fileFeignController.getUserRecentModified() ;
    }
    @GetMapping(value = "/get/recent/group/{groupId}")
    public List<GetFileDto> getGroupRecentModified(@PathVariable String groupId){
        return fileFeignController.getGroupRecentModified(groupId) ;
    }

    @GetMapping(value = "/get/downloads")
    public FilePageDto getDownloads(@RequestParam("page") int page){
        return fileFeignController.getDownloads(page) ;
    }

    @GetMapping(value = "/get/bookmarks")
    public FilePageDto getBookmarks(@RequestParam("page") int page){
        return fileFeignController.getBookmarks(page) ;
    }

    @GetMapping(value = "/search")
    public FilePageDto search(@RequestParam("q") String query , @RequestParam("page") int page){
        return fileFeignController.search(query , page) ;
    }


    @GetMapping(value = "/migrate")
    public void migrate (){
        fileFeignController.migrate();
    }

    @GetMapping(value = "/thumbnail/{fileId}")
    public ResponseEntity<Resource> getThumbnail (@PathVariable String fileId){
        return  fileFeignController.getThumbnail(fileId) ;

    }

    @GetMapping(value = "/get/recommendations")
    public List<GetFileDto> getRecommendations(@RequestParam("q") String query , @RequestParam("fileId") String fileId){
        return fileFeignController.getRecommendations(query , fileId) ;
    }

    @GetMapping(value = "/get/downloads/files/member/{groupId}")
    public FileMetricPageDto getMemberDownloadsByFile(@RequestParam("start") LocalDate start ,
                                                      @RequestParam("end") LocalDate end ,
                                                      @RequestParam("page") int page ,
                                                      @PathVariable String groupId
    )
    {
        return fileFeignController.getMemberDownloadsByFile(start , end , page , groupId) ;
    }

    @GetMapping(value = "/get/views/files/member/{groupId}")
    public FileMetricPageDto getMemberViewsByFile(@RequestParam("start") LocalDate start ,
                                                  @RequestParam("end") LocalDate end ,
                                                  @RequestParam("page") int page ,
                                                  @PathVariable String groupId
    ){
        return fileFeignController.getMemberViewsByFile(start , end , page , groupId) ;
    }

    @DeleteMapping(value = "/file/delete/{fileId}")
    public void userFileDelete(@PathVariable String fileId){
        fileFeignController.userFileDelete(fileId);
    }
    @DeleteMapping(value = "/file/delete/{groupId}/{fileId}")
    public void groupFileDelete(@PathVariable String groupId ,@PathVariable String fileId){
        fileFeignController.groupFileDelete(groupId , fileId);
    }







}

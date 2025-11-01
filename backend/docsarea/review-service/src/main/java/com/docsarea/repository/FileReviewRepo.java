package com.docsarea.repository;

import com.docsarea.enums.Status;
import com.docsarea.module.FileReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileReviewRepo extends JpaRepository <FileReview , String> {

    Optional<FileReview> findFirstByGroupIdOrderByCreatedAtDesc(String groupId) ;
    Page<FileReview> findByModeratorAndGroupIdAndStatus(String Moderator , String groupId , Status status , Pageable page) ;
    Page <FileReview> findByGroupIdAndStatus(String groupId , Status status , Pageable page) ;
    Optional<FileReview> findByFileId(String fileId) ;
    boolean existsByFileIdAndModerator(String fileId , String moderator) ;
    Optional<FileReview> findByGroupIdAndFileId(String groupId , String fileId) ;

}

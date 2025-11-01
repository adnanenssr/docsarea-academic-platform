package com.docsarea.repository;

import com.docsarea.enums.Accessibility;
import com.docsarea.enums.Status;
import com.docsarea.module.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepo extends JpaRepository<Document , String > {
    @NonNull
    Optional <Document> findById(@Nullable String id) ;
    Page <Document> findByOwnerAndGroupIdIsNullOrderByModifiedAtDesc(String owner , Pageable page) ;
    Page <Document> findByOwnerAndGroupIdAndStatus(String owner , String groupId , Status status , Pageable page) ;
    Page <Document> findByGroupIdAndStatusAndAccessibility(String groupId , Status status , Pageable page , Accessibility accessibility) ;
    Page <Document> findByOwnerAndGroupIdIsNullAndStatusAndAccessibilityIn(String username , Status status , Pageable page , List<Accessibility> accessibility) ;
    Page <Document> findByOwnerAndGroupId(String owner , String groupId , Pageable page) ;

    @Query("SELECT COUNT(d.id) FROM Document d " +
            "WHERE d.owner = :owner " +
            "AND d.groupId IS NULL")
    Long getTotalUserFiles(String owner) ;
    @Query("SELECT COUNT(d.id) FROM Document d " +
            "WHERE d.owner = :owner " +
            "AND d.groupId = :group")
    Long getTotalMemberFiles(String owner , String group) ;
    @Query("SELECT COUNT(d.id) FROM Document d " +
            "WHERE d.groupId = :group")
    Long getTotalGroupFiles(String group) ;

    @Query("SELECT SUM(d.storage) FROM Document d " +
            "WHERE d.owner = :owner " +
            "AND d.groupId IS NULL")
    Long getUserUsedSpace(String owner) ;
    @Query("SELECT SUM(d.storage ) FROM Document d " +
            "WHERE d.groupId = :group")
    Long getGroupUsedSpace(String group) ;
    @Query("SELECT d FROM Document d " +
            "WHERE d.owner = :owner AND d.groupId IS NULL " +
            "ORDER BY d.modifiedAt DESC")
    Page<Document> getUserRecentModified (@Param("owner") String owner , Pageable page) ;
    @Query("SELECT d FROM Document d " +
            "WHERE d.groupId = :group " +
            "ORDER BY d.modifiedAt DESC")
    Page<Document> getGroupRecentModified (@Param("group") String group , Pageable page) ;

    boolean existsByOwnerAndGroupIdNotNullAndStatusIsNot(String owner , Status status) ;




}

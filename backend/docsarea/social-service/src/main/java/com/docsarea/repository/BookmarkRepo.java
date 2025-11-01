package com.docsarea.repository;

import com.docsarea.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark , String> {

    @Query("SELECT b.fileId FROM Bookmark b " +
            "WHERE b.username = :username")
    Page<String> getUserBookmarks(@Param("username") String username , Pageable page) ;
    Optional<Bookmark> findByFileIdAndUsername(String fileId , String username ) ;
    boolean existsByUsernameAndFileId(String username , String fileId) ;

}

package com.docsarea.repository;

import com.docsarea.model.Download;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepo extends JpaRepository<Download, String> {

    @Query("SELECT d.fileId FROM Download d " +
            "WHERE d.username = :username")
    Page<String> getUserDownloads(@Param("username") String username , Pageable pageable);
}

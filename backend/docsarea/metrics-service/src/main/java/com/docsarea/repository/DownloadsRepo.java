package com.docsarea.repository;

import com.docsarea.model.Downloads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DownloadsRepo extends JpaRepository<Downloads , String> {
    Optional<Downloads> findByFileIdAndWeek(String FileId , LocalDateTime week);
    List<Downloads> findByFileIdAndWeekBetween(String fileId ,LocalDateTime start , LocalDateTime end) ;
    @Query("SELECT d.week, SUM(d.count) FROM Downloads d " +
            "WHERE d.publisher = :publisher AND d.week BETWEEN :start AND :end " +
            "GROUP BY d.week " +
            "ORDER BY d.week")
    List<Object[]> getTotalDownloadsByWeek(@Param("publisher") String username,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT d.fileId , SUM(d.count) FROM Downloads d" +
            " WHERE d.publisher = :publisher AND d.week BETWEEN :start And :end" +
            " GROUP BY d.fileId" +
            " HAVING SUM(d.count) > 0" +
            " ORDER BY SUM(d.count) DESC")
    Page<Object[]> getTotalDownloadsPerFile(@Param("publisher") String username ,
                                        @Param("start") LocalDateTime start ,
                                        @Param("end") LocalDateTime end ,
                                        Pageable page );

    @Query("SELECT d.week, SUM(d.count) FROM Downloads d " +
            "WHERE d.publisher = :publisher AND d.week BETWEEN :start AND :end " +
            "And d.owner = :owner " +
            "GROUP BY d.week " +
            "ORDER BY d.week")
    List<Object[]> getMemberTotalDownloadsByWeek(@Param("publisher") String publisher,
                                             @Param("owner") String owner ,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("SELECT d.fileId , SUM(d.count) FROM Downloads d" +
            " WHERE d.publisher = :publisher AND d.week BETWEEN :start And :end" +
            " And d.owner = :owner" +
            " GROUP BY d.fileId" +
            " HAVING SUM(d.count) > 0" +
            " ORDER BY SUM(d.count) DESC")
    Page<Object[]> getMemberTotalDownloadsPerFile(@Param("publisher") String publisher ,
                                              @Param("owner") String owner ,
                                              @Param("start") LocalDateTime start ,
                                              @Param("end") LocalDateTime end ,
                                              Pageable page );

    @Query("SELECT SUM(d.count) FROM Downloads d" +
            " WHERE d.publisher = :publisher")
    Optional<Long> getGroupUserTotalDownloads(@Param("publisher") String publisher) ;

    @Query("SELECT SUM(d.count) FROM Downloads d"  +
            " WHERE d.publisher = :publisher AND d.owner = :owner")
    Optional<Long> getMemberTotalDownloads(@Param("publisher") String publisher , @Param("owner") String owner) ;





}

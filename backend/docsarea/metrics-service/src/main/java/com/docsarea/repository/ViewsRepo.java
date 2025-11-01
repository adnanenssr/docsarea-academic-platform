package com.docsarea.repository;

import com.docsarea.model.Views;
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
public interface ViewsRepo extends JpaRepository<Views, String> {
    Optional<Views> findByFileIdAndWeek(String FileId , LocalDateTime week);
    List<Views> findByFileIdAndWeekBetween(String fileId ,LocalDateTime start , LocalDateTime end) ;
    @Query("SELECT v.week, SUM(v.count) FROM Views v " +
            "WHERE v.publisher = :publisher AND v.week BETWEEN :start AND :end " +
            "GROUP BY v.week " +
            "ORDER BY v.week")
    List<Object[]> getTotalViewsByWeek(@Param("publisher") String username,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT v.fileId , SUM(v.count) FROM Views v" +
           " WHERE v.publisher = :publisher AND v.week BETWEEN :start And :end" +
           " GROUP BY v.fileId" +
           " HAVING SUM(v.count) > 0" +
           " ORDER BY SUM(v.count) DESC")
    Page<Object[]> getTotalViewsPerFile(@Param("publisher") String username ,
                                        @Param("start") LocalDateTime start ,
                                        @Param("end") LocalDateTime end ,
                                        Pageable page );

    @Query("SELECT v.week, SUM(v.count) FROM Views v " +
            "WHERE v.publisher = :publisher AND v.week BETWEEN :start AND :end " +
            "And v.owner = :owner " +
            "GROUP BY v.week " +
            "ORDER BY v.week")
    List<Object[]> getMemberTotalViewsByWeek(@Param("publisher") String publisher,
                                       @Param("owner") String owner ,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT v.fileId , SUM(v.count) FROM Views v" +
            " WHERE v.publisher = :publisher AND v.week BETWEEN :start And :end" +
            " And v.owner = :owner" +
            " GROUP BY v.fileId" +
            " HAVING SUM(v.count) > 0" +
            " ORDER BY SUM(v.count) DESC")
    Page<Object[]> getMemberTotalViewsPerFile(@Param("publisher") String publisher ,
                                        @Param("owner") String owner ,
                                        @Param("start") LocalDateTime start ,
                                        @Param("end") LocalDateTime end ,
                                        Pageable page );

    @Query("SELECT SUM(v.count) FROM Views v" +
            " WHERE v.publisher = :publisher")
    Optional<Long> getGroupUserTotalViews(@Param("publisher") String publisher) ;

    @Query("SELECT SUM(v.count) FROM Views v" +
            " WHERE v.publisher = :publisher AND v.owner = :owner")
    Optional<Long> getMemberTotalViews(@Param("publisher") String publisher , @Param("owner") String owner) ;








}

package com.docsarea.service;

import com.docsarea.model.Downloads;
import com.docsarea.repository.DownloadsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
public class AddDownload {
    @Autowired
    DownloadsRepo downloadsRepo;
    public void addDownload(String fileId ,String publisher , String owner ){

        LocalDateTime current = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;

        Optional<Downloads> week = downloadsRepo.findByFileIdAndWeek(fileId , current ) ;

        if(week.isPresent()){
            Downloads thisWeek = week.get() ;
            thisWeek.setCount(thisWeek.getCount() + 1 );
            downloadsRepo.save(thisWeek) ;
        }
        else{
            Downloads download = new Downloads(fileId , current , publisher , owner) ;
            downloadsRepo.save(download) ;
        }

    }

}

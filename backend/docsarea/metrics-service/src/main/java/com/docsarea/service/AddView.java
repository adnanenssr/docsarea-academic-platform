package com.docsarea.service;

import com.docsarea.model.Views;
import com.docsarea.repository.ViewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
public class AddView {
    @Autowired
    ViewsRepo viewsRepo ;
    public void addView(String fileId , String publisher , String owner){

        LocalDateTime current = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;

        Optional<Views> week = viewsRepo.findByFileIdAndWeek(fileId , current) ;

        if(week.isPresent()){
            Views thisWeek = week.get() ;
            thisWeek.setCount(thisWeek.getCount() + 1 );
            viewsRepo.save(thisWeek) ;
        }
        else{
            Views view = new Views(fileId , current , publisher , owner) ;
            viewsRepo.save(view) ;
        }

    }
}

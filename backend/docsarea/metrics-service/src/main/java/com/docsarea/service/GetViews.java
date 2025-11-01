package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.views.ViewsPageDto;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.repository.ViewsRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class GetViews {
    @Autowired
    ViewsRepo viewsRepo ;
    @Autowired
    GroupFeignController groupFeignController ;

    public Map<LocalDate , Long> getUserViewsByWeek( LocalDate beginning , LocalDate ending){

        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;

        if (beginning == null || ending == null) throw new RuntimeException("Please Specify Valid Data") ;

        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> views = viewsRepo.getTotalViewsByWeek(user, start, end) ;
        views.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public Map<LocalDate , Long> getGroupViewsByWeek(String publisher , LocalDate beginning , LocalDate ending){

        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> views = viewsRepo.getTotalViewsByWeek(publisher, start, end) ;
        views.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public MemberDto isMember(String username  , String publisher){
        return groupFeignController.getMember(publisher , username) ;
    }


    public Map<LocalDate , Long> getMemberViewsByWeek(String publisher , String owner , LocalDate beginning , LocalDate ending){



        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> views = viewsRepo.getMemberTotalViewsByWeek(publisher , owner , start, end ) ;
        views.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public Map<LocalDate , Long > groupViewsByWeek(String publisher , LocalDate beginning , LocalDate ending) {
        if (beginning == null || ending == null) throw new RuntimeException("Please Specify Valid Data") ;

        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = isMember(user , publisher);
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return getGroupViewsByWeek(publisher , beginning , ending) ;
        return getMemberViewsByWeek(publisher , user , beginning , ending ) ;


    }






    public ViewsPageDto getTotalViewsByFile(String publisher , LocalDate start , LocalDate end , int page){

        Pageable pageable = PageRequest.of(page , 10) ;
        Map<String , Long> files_views = new LinkedHashMap<>() ;

        LocalDateTime beginning = start.atStartOfDay() ;
        LocalDateTime ending = end.atStartOfDay() ;


        Page<Object[]> views = viewsRepo.getTotalViewsPerFile(publisher , beginning , ending , pageable) ;
        views.forEach((obj -> files_views.put((String) obj[0] ,(Long) obj[1])));

        return new ViewsPageDto(files_views , views.getTotalPages(), views.getTotalElements()) ;


    }

    public ViewsPageDto getMemberTotalViewsByFile( String publisher , String owner , LocalDate start , LocalDate end , int page){

        Pageable pageable = PageRequest.of(page , 10) ;
        Map<String , Long> files_views = new LinkedHashMap<>() ;

        LocalDateTime beginning = start.atStartOfDay() ;
        LocalDateTime ending = end.atStartOfDay() ;


        Page<Object[]> views = viewsRepo.getMemberTotalViewsPerFile(publisher , owner , beginning , ending , pageable) ;
        views.forEach((obj -> files_views.put((String) obj[0] ,(Long) obj[1])));

        return new ViewsPageDto(files_views , views.getTotalPages(), views.getTotalElements()) ;


    }





}

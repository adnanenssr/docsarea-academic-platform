package com.docsarea.service;

import com.docsarea.controller.GroupFeignController;
import com.docsarea.dtos.member.MemberDto;
import com.docsarea.dtos.downloads.DownloadsPageDto;
import com.docsarea.enums.GroupRoles;
import com.docsarea.model.Downloads;
import com.docsarea.repository.DownloadsRepo;
import com.docsarea.utility.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.plaf.ColorUIResource;
import java.lang.reflect.Member;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetDownloads {
    @Autowired
    DownloadsRepo downloadsRepo ;
    @Autowired
    GroupFeignController groupFeignController ;

    public Map<LocalDate , Long> getUserDownloadsByWeek( LocalDate beginning , LocalDate ending){

        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;

        if (beginning == null || ending == null) throw new RuntimeException("Please Specify Valid Data") ;

        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> downloads = downloadsRepo.getTotalDownloadsByWeek(user, start, end) ;
        downloads.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public Map<LocalDate , Long> getGroupDownloadsByWeek(String publisher , LocalDate beginning , LocalDate ending){

        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> downloads = downloadsRepo.getTotalDownloadsByWeek(publisher, start, end) ;
        downloads.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public MemberDto isMember(String username  , String publisher){
        return groupFeignController.getMember(publisher , username) ;
    }


    public Map<LocalDate , Long> getMemberDownloadsByWeek(String publisher , String owner , LocalDate beginning , LocalDate ending){



        Map<LocalDate , Long> history = new TreeMap<>() ;
        LocalDateTime start = beginning.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        LocalDateTime end = ending.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay() ;
        if(start.isAfter(end.minusWeeks(3)) || start.isBefore(end.minusYears(1))) throw new RuntimeException("Please choose a range between 3 weeks and one Year") ;
        List<Object[]> downloads = downloadsRepo.getMemberTotalDownloadsByWeek(publisher , owner , start, end ) ;
        downloads.forEach(obj -> history.put(((LocalDateTime) obj[0]).toLocalDate(), (Long) obj[1]));
        LocalDateTime i ;
        for(i = start ; !i.isAfter(end) ; i=i.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atStartOfDay()){
            history.putIfAbsent(i.toLocalDate() , 0L) ;
        }

        return history ;
    }

    public Map<LocalDate , Long > groupDownloadsByWeek(String publisher , LocalDate beginning , LocalDate ending) {
        if (beginning == null || ending == null) throw new RuntimeException("Please Specify Valid Data") ;

        String user = CurrentAuthenticatedUser.getCurrentAuthenticatedUser() ;
        MemberDto member = isMember(user , publisher);
        if(member.getRole().isHigherThan(GroupRoles.MODERATOR)) return getGroupDownloadsByWeek(publisher , beginning , ending) ;
        return getMemberDownloadsByWeek(publisher , user , beginning , ending ) ;


    }






    public DownloadsPageDto getTotalDownloadsByFile( String publisher , LocalDate start , LocalDate end , int page){

        Pageable pageable = PageRequest.of(page , 10) ;
        Map<String , Long> files_downloads = new LinkedHashMap<>() ;

        LocalDateTime beginning = start.atStartOfDay() ;
        LocalDateTime ending = end.atStartOfDay() ;


        Page<Object[]> downloads = downloadsRepo.getTotalDownloadsPerFile(publisher , beginning , ending , pageable) ;
        downloads.forEach((obj -> files_downloads.put((String) obj[0] ,(Long) obj[1])));

        return new DownloadsPageDto(files_downloads , downloads.getTotalPages(), downloads.getTotalElements()) ;


    }

    public DownloadsPageDto getMemberTotalDownloadsByFile( String publisher , String owner , LocalDate start , LocalDate end , int page){

        Pageable pageable = PageRequest.of(page , 10) ;
        Map<String , Long> files_downloads = new LinkedHashMap<>() ;

        LocalDateTime beginning = start.atStartOfDay() ;
        LocalDateTime ending = end.atStartOfDay() ;


        Page<Object[]> downloads = downloadsRepo.getMemberTotalDownloadsPerFile(publisher , owner , beginning , ending , pageable) ;
        downloads.forEach((obj -> files_downloads.put((String) obj[0] ,(Long) obj[1])));

        return new DownloadsPageDto(files_downloads , downloads.getTotalPages(), downloads.getTotalElements()) ;


    }





}

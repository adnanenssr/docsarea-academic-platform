package com.docsarea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GetStats {
    @Autowired
    GetTotalFiles getTotalFiles ;
    @Autowired
    GetUsedSpace getUsedSpace ;
    public List<String> getUserStats (){
        String[] userStats = { String.valueOf(getTotalFiles.userTotalFiles()) , getUsedSpace.userUsedSpace()} ;
        return Arrays.asList(userStats) ;
    }

    public List<String> getGroupStats (String groupId){
        String[] groupStats = { String.valueOf(getTotalFiles.groupTotalFiles(groupId)) , getUsedSpace.groupUsedSpace(groupId)} ;
        return Arrays.asList(groupStats) ;
    }






}

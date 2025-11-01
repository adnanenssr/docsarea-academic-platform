package com.docsarea.dtos.group;

import java.util.List;

public class GroupPageDto {

    List<GroupInfoDto> groups ;
    Integer numPages ;
    Long numElements ;

    public List<GroupInfoDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupInfoDto> groups) {
        this.groups = groups;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Long getNumElements() {
        return numElements;
    }

    public void setNumElements(Long numElements) {
        this.numElements = numElements;
    }
}

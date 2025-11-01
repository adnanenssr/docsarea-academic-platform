package com.docsarea.dtos.views;

import java.util.Map;

public class ViewsPageDto {
    Map<String , Long> views ;
    int numPages ;
    Long numElements ;

    public ViewsPageDto() {
    }

    public ViewsPageDto(Map<String, Long> views, int numPages, Long numElements) {
        this.views = views;
        this.numPages = numPages;
        this.numElements = numElements;
    }

    public Map<String, Long> getViews() {
        return views;
    }

    public void setViews(Map<String, Long> views) {
        this.views = views;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public Long getNumElements() {
        return numElements;
    }

    public void setNumElements(Long numElements) {
        this.numElements = numElements;
    }
}

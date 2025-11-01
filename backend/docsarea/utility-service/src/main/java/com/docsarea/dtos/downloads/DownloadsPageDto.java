package com.docsarea.dtos.downloads;

import java.util.Map;

public class DownloadsPageDto {
    Map<String , Long> downloads ;
    int numPages ;
    Long numElements ;

    public DownloadsPageDto() {
    }

    public DownloadsPageDto(Map<String, Long> downloads, int numPages, Long numElements) {
        this.downloads = downloads;
        this.numPages = numPages;
        this.numElements = numElements;
    }

    public Map<String, Long> getDownloads() {
        return downloads;
    }

    public void setDownloads(Map<String, Long> downloads) {
        this.downloads = downloads;
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

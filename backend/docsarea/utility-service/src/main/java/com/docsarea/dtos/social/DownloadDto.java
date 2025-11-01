package com.docsarea.dtos.social;

import java.util.List;

public class DownloadDto {
    List<String> download ;
    int numPages ;
    Long numElements ;

    public DownloadDto(List<String> download, int numPages, Long numElements) {
        this.download = download;
        this.numPages = numPages;
        this.numElements = numElements;
    }

    public List<String> getDownload() {
        return download;
    }

    public void setDownload(List<String> download) {
        this.download = download;
    }

    public Long getNumElements() {
        return numElements;
    }

    public void setNumElements(Long numElements) {
        this.numElements = numElements;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}

package com.docsarea.dtos.file;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class FileMetricPageDto {
    List<FileMetricDto> files ;
    int numPages ;
    Long numElements ;

    public FileMetricPageDto(List<FileMetricDto> files, int numPages, Long numElements) {
        this.files = files;
        this.numPages = numPages;
        this.numElements = numElements;
    }

    public FileMetricPageDto() {
    }

    public List<FileMetricDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetricDto> files) {
        this.files = files;
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

package com.docsarea.dtos.file;

import java.util.List;

public class FilePageDto {
    List<GetFileDto> files ;
    Integer numPages ;
    Long numElements ;

    public FilePageDto() {
    }

    public FilePageDto(List<GetFileDto> files, Long numElements, Integer numPages) {
        this.files = files;
        this.numElements = numElements;
        this.numPages = numPages;
    }

    public List<GetFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<GetFileDto> files) {
        this.files = files;
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

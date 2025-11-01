package com.docsarea.dtos.file;

import java.util.List;

public class FileModeratorPageDto {
    List<FileModeratorDto> files ;
    int numPages ;
    Long numElements ;

    public List<FileModeratorDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileModeratorDto> files) {
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

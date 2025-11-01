package com.docsarea.dtos.review;

import java.util.Set;

public class ReviewPageDto {
    Set<FileIdModeratorDto> reviews ;
    int numPages ;
    Long numElements ;

    public Set<FileIdModeratorDto> getReviews() {
        return reviews;
    }

    public void setReviews(Set<FileIdModeratorDto> reviews) {
        this.reviews = reviews;
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

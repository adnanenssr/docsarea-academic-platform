package com.docsarea.dtos.review;

import com.docsarea.enums.Status;

public class UpdateReview {
    String comment ;
    Status status ;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

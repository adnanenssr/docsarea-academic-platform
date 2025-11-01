package com.docsarea.dtos.file;

import com.docsarea.enums.Accessibility;
import com.docsarea.enums.Status;

import java.util.List;

public class UserFileUploadDto {
    String title ;
    String description ;
    String owner ;
    boolean downloadable ;
    Status status ;
    Accessibility accessibility ;
    List <String> authors ;
    List <String> shared ;

    public UserFileUploadDto(String title, String description, String owner, boolean downloadable, Status status, Accessibility accessibility, List<String> authors, List<String> shared) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.downloadable = downloadable;
        this.status = status;
        this.accessibility = accessibility;
        this.authors = authors;
        this.shared = shared;
    }

    public UserFileUploadDto() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authros) {
        this.authors = authros;
    }

    public List<String> getShared() {
        return shared;
    }

    public void setShared(List<String> shared) {
        this.shared = shared;
    }
}

package com.docsarea.dtos.file;

import com.docsarea.enums.Accessibility;
import com.docsarea.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FileUploadDto {
    @NotBlank
    String title ;
    @NotBlank
    String description ;
    @NotBlank
    String owner ;
    String groupId ;
    Status status ;
    boolean downloadable ;
    @NotNull
    Accessibility accessibility ;
    List<String> authors ;
    List <String> shared ;


    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getGroup() {
        return groupId;
    }

    public void setGroup(String group) {
        this.groupId = group;
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

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getShared() {
        return shared;
    }

    public void setShared(List<String> shared) {
        this.shared = shared;
    }
}

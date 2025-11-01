package com.docsarea.dtos.file;

import com.docsarea.enums.Status;

import java.util.List;

public class FileMetricDto {
    String id ;
    String owner ;
    String title ;
    String description ;
    String groupId ;
    String fileName ;
    String size ;
    Status status ;
    String extension ;
    boolean downloadable ;
    List<String > authors ;
    List <String > shared ;
    Long metric ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    public Long getMetric() {
        return metric;
    }

    public void setMetric(Long metric) {
        this.metric = metric;
    }
}

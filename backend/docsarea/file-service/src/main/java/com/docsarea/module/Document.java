package com.docsarea.module;

import com.docsarea.enums.Accessibility;
import com.docsarea.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Document {
    @Id
    String id ;
    String title ;
    String description ;
    String fileName ;
    String extension ;
    String filePath ;
    @Enumerated(EnumType.STRING)
    Status status ;
    String owner ;
    String groupId ;
    @Enumerated(EnumType.STRING)
    Accessibility accessibility ;
    List <String> authors ;
    List <String> shared ;
    boolean downloadable ;
    LocalDateTime createdAt ;
    LocalDateTime modifiedAt ;
    Long storage ;
    String thumbnail ;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String url) {
        this.filePath = url;
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

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();

    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @PreUpdate
    public void setModifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }
}

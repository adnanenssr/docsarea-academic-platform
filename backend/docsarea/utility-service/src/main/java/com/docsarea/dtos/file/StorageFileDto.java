package com.docsarea.dtos.file;

import jakarta.validation.constraints.NotBlank;

public class StorageFileDto {
    @NotBlank
    String thumbnail ;
    @NotBlank
    String filePath ;
    @NotBlank
    String extension ;
    @NotBlank
    String originalFileName ;
    @NotBlank
    Long storage ;



    public StorageFileDto(String filePath, String extension, String originalFileName, Long storage , String thumbnail) {
        this.filePath = filePath;
        this.extension = extension;
        this.originalFileName = originalFileName;
        this.storage = storage;
        this.thumbnail = thumbnail ;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail( String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getStorage() {
        return storage;
    }

    public void setStorage(Long storage) {
        this.storage = storage;
    }
}

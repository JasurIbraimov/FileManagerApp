package com.jasur.files;

public class FileModel {
    private String fileName;
    private String title;
    private String content;
    private String extension;

    public FileModel(String fileName, String title, String content, String extension) {
        this.fileName = fileName;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

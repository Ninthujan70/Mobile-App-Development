package com.liepu.finalassignment.third.models;

public class Song {
    String name, fileUrl;

    public Song(String name, String fileUrl) {
        this.name = name;
        this.fileUrl = fileUrl;
    }

    public String getName() {
        return name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
package com.liepu.finalassignment.third.models;

public class Artist {
    String name, fileUrl;

    public Artist(String name, String fileUrl) {
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

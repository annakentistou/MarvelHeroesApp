package com.codehub.marvelheroes;

import android.media.Image;

public class HeroesInfo {
    private String name;
    private String description;
    /*private Image thumbnail;*/
    private String path;
    private String extension;
    private int id;

    public HeroesInfo() {
    }

    public HeroesInfo(String name) {
        this.name = name;
    }

   /* public HeroesInfo(String name, String description, String path, String extension) {
        this.name = name;
        this.description = description;
        *//*this.thumbnail = thumbnail;*//*
        this.path = path;
        this.extension = extension;
    }*/



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }*/

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

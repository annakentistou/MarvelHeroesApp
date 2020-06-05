package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class HeroesModel {
    private int id;
    private String name;
    private String description;
    private ImageModel thumbnail;
    private NumOfComics comics;
    private NumOfSeries series;
    //For favoriteList
    private int favStatus;

    public HeroesModel(){

    }

    public HeroesModel(int id, String name, String description, ImageModel thumbnail, NumOfComics comics, NumOfSeries series, int favStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.comics = comics;
        this.series = series;
        this.favStatus = favStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ImageModel getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageModel thumbnail) {
        this.thumbnail = thumbnail;
    }

    public NumOfComics getComics() {
        return comics;
    }

    public void setComics(NumOfComics comics) {
        this.comics = comics;
    }

    public NumOfSeries getSeries() {
        return series;
    }

    public void setSeries(NumOfSeries series) {
        this.series = series;
    }

    public int getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(int favStatus) {
        this.favStatus = favStatus;
    }
}

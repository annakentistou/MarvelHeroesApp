package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class HeroesModel {
    private String name;
    private String description;
    private ImageModel thumbnail;
    private NumOfComics comics;
    private NumOfSeries series;

    public HeroesModel(String name, String description, ImageModel thumbnail, NumOfComics comics, NumOfSeries series) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.comics = comics;
        this.series = series;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Nullable
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
}

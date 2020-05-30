package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class HeroesModel {
    private String name;
    private String description;
    private ImageModel thumbnail;

    public HeroesModel(String name, String description, ImageModel thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
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
}

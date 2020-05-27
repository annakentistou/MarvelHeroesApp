package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class ComicsModel {
    private String title;
    private String description;
    private ImageModel thumbnail;
    private CreatorsModel creators;

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Nullable
    public CreatorsModel getCreators() {
        return creators;
    }

    public void setCreators(CreatorsModel creators) {
        this.creators = creators;
    }
}

package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class ImageModel {
    private String path;
    private String extension;

    @Nullable
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Nullable
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

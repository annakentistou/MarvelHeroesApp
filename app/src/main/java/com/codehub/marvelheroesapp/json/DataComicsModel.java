package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class DataComicsModel {
    private DataComicsListModel data;

    @Nullable
    public DataComicsListModel getData() {
        return data;
    }

    public void setData(DataComicsListModel data) {
        this.data = data;
    }
}

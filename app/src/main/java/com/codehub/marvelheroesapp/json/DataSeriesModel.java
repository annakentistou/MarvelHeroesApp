package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

public class DataSeriesModel {
    private DataSeriesListModel data;

    @Nullable
    public DataSeriesListModel getData() {
        return data;
    }

    public void setData(DataSeriesListModel data) {
        this.data = data;
    }
}

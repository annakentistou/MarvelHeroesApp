package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

import java.util.List;

public class DataModel {

    private DataListModel data;

    @Nullable
    public DataListModel getData() {
        return data;
    }

    public void setData(DataListModel data) {
        this.data = data;
    }
}

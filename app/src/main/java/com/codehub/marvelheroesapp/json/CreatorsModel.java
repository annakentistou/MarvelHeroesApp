package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

import java.util.List;

public class CreatorsModel {
    private List<CreatorsNameModel> items;

    public CreatorsModel(List<CreatorsNameModel> items) {
        this.items = items;
    }

    @Nullable
    public List<CreatorsNameModel> getItems() {
        return items;
    }

    public void setItems(List<CreatorsNameModel> items) {
        this.items = items;
    }
}

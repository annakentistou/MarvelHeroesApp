package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

import java.util.List;

public class DataListModel {
    private List<HeroesModel> results;

    public DataListModel() {
    }

    public DataListModel(List<HeroesModel> results) {
        this.results = results;
    }

    @Nullable
    public List<HeroesModel> getResults() {
        return results;
    }

    public void setResults(List<HeroesModel> results) {
        this.results = results;
    }
}

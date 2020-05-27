package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

import java.util.List;

public class DataComicsListModel {
    private List<ComicsModel> results;

    @Nullable
    public List<ComicsModel> getResults() {
        return results;
    }

    public void setResults(List<ComicsModel> results) {
        this.results = results;
    }
}

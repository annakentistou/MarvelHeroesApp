package com.codehub.marvelheroesapp.json;

import androidx.annotation.Nullable;

import java.util.List;

public class DataSeriesListModel {
    private List<SeriesModel> results;

    @Nullable
    public List<SeriesModel> getResults() {
        return results;
    }

    public void setResults(List<SeriesModel> results) {
        this.results = results;
    }
}

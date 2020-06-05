package com.codehub.marvelheroesapp.DatabaseFiles;

public class FavoriteHero {

    private String item_title;
    private int id;
    private String item_image;

    public FavoriteHero() {
    }

    public FavoriteHero(String item_title, int id, String item_image) {
        this.item_title = item_title;
        this.id = id;
        this.item_image = item_image;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
}

package com.example.btl_nhom3.feature_menu.model;

public class Category {
    private int id;
    private String name;
    private int image;

    public Category(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}

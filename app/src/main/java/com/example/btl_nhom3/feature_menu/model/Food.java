package com.example.btl_nhom3.feature_menu.model;

public class Food {

    private int id;
    private String name;
    private int price;
    private int categoryId;

    private int image;
    private String description;
    private int quantity;

    public Food(int id, String name, int price, int categoryId,
                int image, String description, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getCategoryId() { return categoryId; }
    public int getImage() { return image; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
}

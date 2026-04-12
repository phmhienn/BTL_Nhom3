package com.example.btl_nhom3.feature_cart.model;

public class CartItem {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public CartItem(int id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Backward-compatible constructor for old call sites.
    public CartItem(String name, int price, int quantity) {
        this(0, name, price, quantity);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public int getSubtotal() {
        return price * quantity;
    }
}
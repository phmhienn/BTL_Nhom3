package com.example.btl_nhom3.feature_order.model;

public class OrderItem {
    private int id;
    private int orderId;
    private int foodId;
    private int quantity;
    private int price;

    public OrderItem(int id, int orderId, int foodId, int quantity, int price) {
        this.id = id;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters (tương tự như trên)
    public int getId() { return id; }
    public int getOrderId() { return orderId; }
    public int getFoodId() { return foodId; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }

    public void setId(int id) { this.id = id; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(int price) { this.price = price; }
}

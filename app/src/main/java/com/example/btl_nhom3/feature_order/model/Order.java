package com.example.btl_nhom3.feature_order.model;

public class Order {
    private int id;
    private int userId;
    private int totalPrice;
    private String status;
    private String createdAt;
    private String address;

    public Order(int id, int userId, int totalPrice, String status, String createdAt, String address) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.address = address;
    }

    // Các Getter
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public String getAddress() { return address; }
}

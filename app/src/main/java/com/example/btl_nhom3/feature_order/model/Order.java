package com.example.btl_nhom3.feature_order.model;

public class Order {
    private int id;
    private String username;
    private int totalPrice;
    private String status; // VD: "Pending", "Processing", "Delivered"
    private String createdAt;
    private String address;
    private String phone;

    public Order(int id, String username, int totalPrice, String status, String createdAt, String address, String phone) {
        this.id = id;
        this.username = username;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.address = address;
        this.phone = phone;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
}


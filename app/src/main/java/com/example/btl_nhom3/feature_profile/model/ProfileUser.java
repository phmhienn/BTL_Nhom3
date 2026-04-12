package com.example.btl_nhom3.feature_profile.model;

public class ProfileUser {
    private final int id;
    private final String username;
    private final String fullName;
    private final String phone;
    private final String address;

    public ProfileUser(int id, String username, String fullName, String phone, String address) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}


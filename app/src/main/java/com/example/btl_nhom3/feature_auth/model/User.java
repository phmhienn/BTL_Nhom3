package com.example.btl_nhom3.feature_auth.model;

public class User {
	private final int id;
	private final String username;
	private final String fullName;
	private final String phone;
	private final String address;
	private final String role;

	public User(int id, String username, String fullName, String phone, String address, String role) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.phone = phone;
		this.address = address;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public boolean isAdmin() {
		return "admin".equalsIgnoreCase(role);
	}
}

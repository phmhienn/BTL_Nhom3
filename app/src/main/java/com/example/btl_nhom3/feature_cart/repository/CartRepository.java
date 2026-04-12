package com.example.btl_nhom3.feature_cart.repository;

import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private final List<CartItem> cartItems = new ArrayList<>();

    public CartRepository() {
        cartItems.add(new CartItem(1, "Burger bò", 50000, 2));
        cartItems.add(new CartItem(2, "Pizza hải sản", 120000, 1));
        cartItems.add(new CartItem(3, "Trà sữa", 30000, 3));
    }

    public List<CartItem> getCart() {
        return new ArrayList<>(cartItems);
    }

    public void updateQuantity(int itemId, int quantity) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getId() == itemId) {
                if (quantity <= 0) {
                    cartItems.remove(i);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }

    public void removeItem(int itemId) {
        updateQuantity(itemId, 0);
    }

    public int calculateTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }
}
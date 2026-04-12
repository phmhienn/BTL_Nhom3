package com.example.btl_nhom3.feature_cart.repository;

import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private static final int MIN_QUANTITY = 0;
    private static final int MAX_QUANTITY = 100;
    private static CartRepository instance;

    private final List<CartItem> cartItems = new ArrayList<>();

    private CartRepository() {
    }

    public static synchronized CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    public void addItem(int itemId, String name, int price) {
        for (CartItem item : cartItems) {
            if (item.getId() == itemId) {
                int nextQuantity = Math.min(MAX_QUANTITY, item.getQuantity() + 1);
                item.setQuantity(nextQuantity);
                return;
            }
        }

        cartItems.add(new CartItem(itemId, name, price, 1));
    }

    public List<CartItem> getCart() {
        return new ArrayList<>(cartItems);
    }

    public void updateQuantity(int itemId, int quantity) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getId() == itemId) {
                int safeQuantity = Math.max(MIN_QUANTITY, Math.min(MAX_QUANTITY, quantity));
                if (safeQuantity <= 0) {
                    cartItems.remove(i);
                } else {
                    item.setQuantity(safeQuantity);
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
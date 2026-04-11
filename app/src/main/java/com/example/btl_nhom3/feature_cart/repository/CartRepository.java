package com.example.btl_nhom3.feature_cart.repository;

import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    public List<CartItem> getCart() {
        List<CartItem> list = new ArrayList<>();

        list.add(new CartItem("Burger bò", 50000, 2));
        list.add(new CartItem("Pizza hải sản", 120000, 1));
        list.add(new CartItem("Trà sữa", 30000, 3));

        return list;
    }
}
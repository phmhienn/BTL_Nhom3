package com.example.btl_nhom3.feature_menu.repository;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_menu.model.Category;
import com.example.btl_nhom3.feature_menu.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    // CATEGORY
    public List<Category> getCategories() {
        List<Category> list = new ArrayList<>();

        list.add(new Category(0, "Tất cả", R.drawable.ic_launcher_background));
        list.add(new Category(1, "Đồ ăn", R.drawable.ic_launcher_background));
        list.add(new Category(2, "Đồ uống", R.drawable.ic_launcher_background));

        return list;
    }

    // FOOD
    public List<Food> getFoods() {
        List<Food> list = new ArrayList<>();

        list.add(new Food(1, "Burger bò", 50000, 1,
                R.drawable.ic_launcher_background,
                "Burger bò siêu ngon với phô mai", 10));

        list.add(new Food(2, "Pizza hải sản", 120000, 1,
                R.drawable.ic_launcher_background,
                "Pizza đầy topping hải sản", 5));

        list.add(new Food(3, "Trà sữa", 30000, 2,
                R.drawable.ic_launcher_background,
                "Trà sữa trân châu đường đen", 20));

        return list;
    }
}
package com.example.btl_nhom3.feature_menu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.btl_nhom3.feature_menu.model.Category;
import com.example.btl_nhom3.feature_menu.model.Food;
import com.example.btl_nhom3.feature_menu.repository.MenuRepository;

import java.util.List;

public class MenuViewModel extends ViewModel {

    private MenuRepository repository = new MenuRepository();

    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private MutableLiveData<List<Food>> foods = new MutableLiveData<>();

    public void loadData() {
        categories.setValue(repository.getCategories());
        foods.setValue(repository.getFoods());
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }
}
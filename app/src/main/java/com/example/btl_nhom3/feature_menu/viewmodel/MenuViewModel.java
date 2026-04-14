package com.example.btl_nhom3.feature_menu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.btl_nhom3.feature_menu.model.Category;
import com.example.btl_nhom3.feature_menu.model.Food;
import com.example.btl_nhom3.feature_menu.repository.MenuRepository;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {

    private final MenuRepository repository;

    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<List<Food>> foods = new MutableLiveData<>();

    public MenuViewModel(@NonNull Application application) {
        super(application);
        repository = new MenuRepository(application);
    }

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

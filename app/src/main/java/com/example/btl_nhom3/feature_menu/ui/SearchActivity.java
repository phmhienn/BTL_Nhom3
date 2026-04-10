package com.example.btl_nhom3.feature_menu.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_menu.adapter.FoodAdapter;
import com.example.btl_nhom3.feature_menu.model.Food;
import com.example.btl_nhom3.feature_menu.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearch;
    RecyclerView rvSearch;

    FoodAdapter adapter;
    List<Food> fullList;
    List<Food> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        rvSearch = findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        MenuRepository repo = new MenuRepository();
        fullList = repo.getFoods();
        filteredList = new ArrayList<>(fullList);

        adapter = new FoodAdapter(filteredList);
        rvSearch.setAdapter(adapter);

        // nhận keyword từ màn trước
        String keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            edtSearch.setText(keyword);
            filter(keyword);
        }

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        filteredList.clear();

        for (Food f : fullList) {
            if (f.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(f);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
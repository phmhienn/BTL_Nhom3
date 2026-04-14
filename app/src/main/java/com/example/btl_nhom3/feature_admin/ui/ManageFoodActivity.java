package com.example.btl_nhom3.feature_admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.adapter.AdminFoodAdapter;
import com.example.btl_nhom3.feature_admin.model.Food;
import com.example.btl_nhom3.feature_admin.repository.AdminRepository;

import java.util.List;

public class ManageFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminFoodAdapter adapter;
    private AdminRepository repository;
    private Button btnAdd;
    private ImageView btnSearch;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim();

            if (adapter != null) {
                adapter.filter(keyword);
            }
        });

        recyclerView = findViewById(R.id.recyclerViewFood);
        btnAdd = findViewById(R.id.btnAddFood);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        repository = new AdminRepository(this);

        loadData();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ManageFoodActivity.this, AddFoodActivity.class);
            startActivity(intent);

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<Food> list = repository.getAllFood();
        adapter = new AdminFoodAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
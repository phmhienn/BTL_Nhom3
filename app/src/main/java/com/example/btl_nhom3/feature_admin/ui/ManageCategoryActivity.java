package com.example.btl_nhom3.feature_admin.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.adapter.CategoryAdapter_Admin;
import com.example.btl_nhom3.feature_admin.repository.CategoryRepository;
import com.example.btl_nhom3.feature_menu.model.Category;

import java.util.List;

public class ManageCategoryActivity extends AppCompatActivity {

    RecyclerView rvCategory;
    ImageView btnAdd;

    CategoryRepository repo;
    CategoryAdapter_Admin adapter;
    List<Category> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        rvCategory = findViewById(R.id.rvCategory);
        btnAdd = findViewById(R.id.btnAdd);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        repo = new CategoryRepository(this);

        rvCategory.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        btnAdd.setOnClickListener(v -> {
            EditText edt = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Thêm danh mục")
                    .setView(edt)
                    .setPositiveButton("OK", (dialog, which) -> {

                        int id = (int) System.currentTimeMillis();

                        repo.insert(new Category(id, edt.getText().toString(), R.drawable.ic_launcher_background));

                        loadData();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void loadData() {
        list = repo.getAll();
        adapter = new CategoryAdapter_Admin(this, list);
        rvCategory.setAdapter(adapter);
    }
}

package com.example.btl_nhom3.feature_admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_auth.ui.LoginActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout btnManageFood, btnManageOrder, btnManageUser, btnManageCategory;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnManageFood = findViewById(R.id.btnManageFood);
        btnManageOrder = findViewById(R.id.btnManageOrder);
        btnManageUser = findViewById(R.id.btnManageUser);
        btnManageCategory = findViewById(R.id.btnManageCategory);
        btnLogout = findViewById(R.id.btnLogout);

        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Quản lý món
        btnManageFood.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageFoodActivity.class));
        });

        // Quản lý đơn
        btnManageOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageOrderActivity.class));
        });

        // Quản lý tài khoản
        btnManageUser.setOnClickListener(v -> {
            // startActivity(new Intent(this, ManageUserActivity.class));
        });

        // Quản lý danh mục
        btnManageCategory.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageCategoryActivity.class));
        });
    }
}
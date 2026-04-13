package com.example.btl_nhom3.feature_admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout btnManageFood, btnManageOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnManageFood = findViewById(R.id.btnManageFood);
        btnManageOrder = findViewById(R.id.btnManageOrder);

        // 👉 Quản lý món
        btnManageFood.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageFoodActivity.class));
        });

        // 👉 Quản lý đơn
        btnManageOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageOrderActivity.class));
        });
    }
}
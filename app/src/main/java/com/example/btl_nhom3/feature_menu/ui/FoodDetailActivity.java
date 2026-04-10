package com.example.btl_nhom3.feature_menu.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView imgFood, btnBack;
    TextView txtName, txtPrice, txtDesc, txtQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        imgFood = findViewById(R.id.imgFood);
        btnBack = findViewById(R.id.btnBack);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        txtQuantity = findViewById(R.id.txtQuantity);

        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price", 0);
        int image = getIntent().getIntExtra("image", 0);
        String desc = getIntent().getStringExtra("desc");
        int quantity = getIntent().getIntExtra("quantity", 0);

        txtName.setText(name);
        txtPrice.setText(price + "đ");
        txtDesc.setText(desc);
        txtQuantity.setText("Còn lại: " + quantity);

        imgFood.setImageResource(image);

        btnBack.setOnClickListener(v -> finish());
    }
}
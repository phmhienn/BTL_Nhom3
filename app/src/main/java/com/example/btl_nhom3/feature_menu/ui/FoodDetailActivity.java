package com.example.btl_nhom3.feature_menu.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.repository.CartRepository;

public class FoodDetailActivity extends AppCompatActivity {

    ImageView imgFood, btnBack;
    TextView txtName, txtPrice, txtDesc, txtQuantity;
    Button btnAddToCart;

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
        btnAddToCart = findViewById(R.id.btnAddToCart);

        int id = getIntent().getIntExtra("id", -1);
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

        btnAddToCart.setOnClickListener(v -> {
            CartRepository.getInstance().addItem(this, id, name, price);
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });
    }
}
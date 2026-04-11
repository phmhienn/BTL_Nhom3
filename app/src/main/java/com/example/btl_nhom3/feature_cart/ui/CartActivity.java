package com.example.btl_nhom3.feature_cart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.adapter.CartAdapter;
import com.example.btl_nhom3.feature_cart.model.CartItem;
import com.example.btl_nhom3.feature_cart.repository.CartRepository;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    TextView txtTotal;
    Button btnCheckout;

    CartAdapter adapter;
    List<CartItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        txtTotal = findViewById(R.id.txtTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // lấy data
        CartRepository repo = new CartRepository();
        cartList = repo.getCart();

        // adapter
        adapter = new CartAdapter(cartList);
        rvCart.setAdapter(adapter);

        // tính tổng tiền
        updateTotal();

        // nút thanh toán
        btnCheckout.setOnClickListener(v -> {
            startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    private void updateTotal() {
        int total = 0;

        for (CartItem item : cartList) {
            total += item.getPrice() * item.getQuantity();
        }

        txtTotal.setText("Tổng: " + total + "đ");
    }
}
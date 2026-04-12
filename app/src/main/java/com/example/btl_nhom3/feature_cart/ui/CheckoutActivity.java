package com.example.btl_nhom3.feature_cart.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;

public class CheckoutActivity extends AppCompatActivity {

    TextView txtCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtCheckout = findViewById(R.id.txtCheckout);

        int total = getIntent().getIntExtra(CartActivity.EXTRA_TOTAL, 0);
        int itemCount = getIntent().getIntExtra(CartActivity.EXTRA_ITEM_COUNT, 0);

        if (itemCount <= 0) {
            txtCheckout.setText("Giỏ hàng trống, chưa thể thanh toán");
            return;
        }

        txtCheckout.setText("Thanh toán " + itemCount + " món - Tổng: " + total + "đ");
    }
}
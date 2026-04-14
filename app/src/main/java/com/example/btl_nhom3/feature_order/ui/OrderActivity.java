package com.example.btl_nhom3.feature_order.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.repository.OrderRepository;

public class OrderActivity extends AppCompatActivity {
    EditText edtAddress, edtPhone;
    Button btnConfirm;
    TextView tvTotal;
    OrderRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        repository = new OrderRepository(this);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        btnConfirm = findViewById(R.id.btnPlaceOrder);
        tvTotal = findViewById(R.id.tvOrderSummary);

        // Giả sử tổng tiền được truyền từ màn hình Giỏ hàng
        int total = getIntent().getIntExtra("TOTAL_AMOUNT", 0);
        tvTotal.setText("Tổng thanh toán: " + total + " VNĐ");

        btnConfirm.setOnClickListener(v -> {
            String addr = edtAddress.getText().toString();
            String phone = edtPhone.getText().toString();

            // Lấy username từ SharedPreferences (Bạn cần lưu lúc đăng nhập)
            String user = getSharedPreferences("UserFile", MODE_PRIVATE).getString("username", "user");

            if (!addr.isEmpty() && !phone.isEmpty()) {
                long id = repository.checkout(user, total, addr, phone);
                if (id != -1) {
                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
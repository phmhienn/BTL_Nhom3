package com.example.btl_nhom3.feature_order.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.adapter.OrderDetailAdapter;
import com.example.btl_nhom3.feature_order.repository.OrderRepository;
import java.util.List;
import java.util.Map;

public class OrderStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_order_status);

        // 1. Ánh xạ các View từ XML
        TextView tvInfo = findViewById(R.id.tvDetailTitle);
        TextView tvAddress = findViewById(R.id.tvDetailAddress); // BẠN THIẾU DÒNG NÀY
        TextView tvTotal = findViewById(R.id.tvDetailTotal);


        // 2. Nhận dữ liệu từ Intent (phải khớp Key với OrderAdapter)
        int id = getIntent().getIntExtra("ID", -1);
        String address = getIntent().getStringExtra("ADDR"); // Nhận địa chỉ
        int total = getIntent().getIntExtra("TOTAL", 0);

        // 3. Hiển thị lên UI
        tvInfo.setText("Chi tiết đơn #" + id);

        if (address != null && !address.isEmpty()) {
            tvAddress.setText("Địa chỉ: " + address);
        } else {
            tvAddress.setText("Địa chỉ: Chưa cập nhật");
        }

        tvTotal.setText("Tổng: " + String.format("%, d", total) + "đ");
    }
}
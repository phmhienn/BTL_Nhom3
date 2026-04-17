package com.example.btl_nhom3.feature_order.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; // THÊM IMPORT

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.adapter.OrderDetailAdapter; // THÊM IMPORT
import com.example.btl_nhom3.feature_order.repository.OrderRepository; // THÊM IMPORT

import java.util.List;
import java.util.Map;

public class OrderStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_order_status);

        // 1. Ánh xạ các View từ XML
        TextView tvInfo = findViewById(R.id.tvDetailTitle);
        TextView tvAddress = findViewById(R.id.tvDetailAddress);
        TextView tvTotal = findViewById(R.id.tvDetailTotal);
        RecyclerView rvOrderItems = findViewById(R.id.rvOrderItems); // ÁNH XẠ RECYCLERVIEW MỚI

        // 2. Nhận dữ liệu chung từ Intent
        int id = getIntent().getIntExtra("ID", -1);
        String address = getIntent().getStringExtra("ADDR");
        int total = getIntent().getIntExtra("TOTAL", 0);

        // 3. Hiển thị thông tin chung
        tvInfo.setText("Chi tiết đơn #" + id);
        if (address != null && !address.isEmpty()) {
            tvAddress.setText("Địa chỉ: " + address);
        } else {
            tvAddress.setText("Địa chỉ: Chưa cập nhật");
        }
        tvTotal.setText("Tổng cộng: " + String.format("%, d", total) + " VNĐ");

        // ==========================================
        // 4. LẤY VÀ HIỂN THỊ DANH SÁCH MÓN ĂN TỪ DATABASE
        // ==========================================
        if (id != -1) {
            // Cài đặt RecyclerView dạng danh sách dọc
            rvOrderItems.setLayoutManager(new LinearLayoutManager(this));

            // Khởi tạo Repository và lấy chi tiết đơn
            OrderRepository repository = new OrderRepository(this);
            List<Map<String, Object>> listItems = repository.getOrderDetails(id);

            // Gắn Adapter
            OrderDetailAdapter adapter = new OrderDetailAdapter(listItems);
            rvOrderItems.setAdapter(adapter);
        }
    }
}
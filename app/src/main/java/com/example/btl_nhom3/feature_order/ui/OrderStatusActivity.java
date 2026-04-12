package com.example.btl_nhom3.feature_order.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_nhom3.R;

public class OrderStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        TextView tvOrderId = findViewById(R.id.tvDetailOrderId);
        TextView tvAddress = findViewById(R.id.tvDetailAddress);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);

        // Nhận dữ liệu từ Intent
        int orderId = getIntent().getIntExtra("ORDER_ID", 0);
        String status = getIntent().getStringExtra("STATUS");
        String address = getIntent().getStringExtra("ADDRESS");

        tvOrderId.setText("Mã đơn: #" + orderId);
        tvAddress.setText("Địa chỉ: " + address);
        tvStatus.setText("Trạng thái: " + status);
    }
}

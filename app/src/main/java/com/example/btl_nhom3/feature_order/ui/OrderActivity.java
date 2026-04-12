package com.example.btl_nhom3.feature_order.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.repository.OrderRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private EditText edtAddress;
    private TextView tvCheckoutTotal;
    private Button btnPlaceOrder;
    private OrderRepository orderRepository;
    private int totalPrice = 0;
    private int userId = 1; // Giả sử ID người dùng là 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 1. Ánh xạ view từ XML
        edtAddress = findViewById(R.id.edtAddress);
        tvCheckoutTotal = findViewById(R.id.tvCheckoutTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        orderRepository = new OrderRepository(this);

        // 2. Nhận tổng tiền truyền từ màn hình Giỏ hàng (nếu có)
        totalPrice = getIntent().getIntExtra("TOTAL_PRICE", 0);
        tvCheckoutTotal.setText("Tổng thanh toán: " + totalPrice + " đ");

        // 3. Xử lý sự kiện nút Đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            String address = edtAddress.getText().toString().trim();

            if (address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            confirmOrder(address);
        });
    }

    private void confirmOrder(String address) {
        // Lấy ngày hiện tại
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Gọi hàm lưu vào Database
        long result = orderRepository.placeOrder(userId, totalPrice, address, currentDate);

        if (result != -1) {
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
            // Đóng màn hình này để quay lại màn hình chính hoặc lịch sử
            finish();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }
}

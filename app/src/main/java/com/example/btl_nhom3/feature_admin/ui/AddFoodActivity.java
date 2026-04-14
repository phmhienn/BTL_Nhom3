package com.example.btl_nhom3.feature_admin.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.model.Food;
import com.example.btl_nhom3.feature_admin.repository.AdminRepository;

public class AddFoodActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDesc,edtQuantity;
    private Button btnSave;
    private AdminRepository repository;
    ImageView imgFood;
    Button btnChooseImage;
    String imagePath = "";

    private int foodId = -1; // 👉 phân biệt thêm / sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDesc = findViewById(R.id.edtDesc);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnSave = findViewById(R.id.btnSave);
        imgFood = findViewById(R.id.imgFood);

        imgFood.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        repository = new AdminRepository(this);

        // ================= NHẬN DATA KHI SỬA =================
        foodId = getIntent().getIntExtra("id", -1);

        if (foodId != -1) {
            edtName.setText(getIntent().getStringExtra("name"));
            edtPrice.setText(String.valueOf(getIntent().getIntExtra("price", 0)));
            edtQuantity.setText(String.valueOf(getIntent().getIntExtra("quantity", 0)));
            edtDesc.setText(getIntent().getStringExtra("desc"));

            // 👉 KHÓA tên món (không cho sửa)
            edtName.setEnabled(false);
        }

        // ================= NÚT SAVE =================
        btnSave.setOnClickListener(v -> {

            String priceStr = edtPrice.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();
            String quantityStr = edtQuantity.getText().toString().trim();

            if (TextUtils.isEmpty(quantityStr)) {
                Toast.makeText(this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);

            if (TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Nhập giá", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);

            String name;

            if (foodId == -1) {
                // 👉 THÊM
                name = edtName.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "Nhập tên món", Toast.LENGTH_SHORT).show();
                    return;
                }
                imagePath = getIntent().getStringExtra("image");

                if (imagePath != null && !imagePath.isEmpty()) {
                    imgFood.setImageURI(Uri.parse(imagePath));
                }

                Food food = new Food(name, price, desc,imagePath, quantity, 1);
                repository.insertFood(food);

                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();

            } else {
                // 👉 SỬA (giữ nguyên tên)
                name = getIntent().getStringExtra("name");

                Food food = new Food(name, price, desc, "default", quantity, 1);
                food.setId(foodId); // ⚠️ QUAN TRỌNG

                repository.updateFood(food);

                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imgFood.setImageURI(uri);

            imagePath = uri.toString(); // 👉 lưu path
        }
    }
}
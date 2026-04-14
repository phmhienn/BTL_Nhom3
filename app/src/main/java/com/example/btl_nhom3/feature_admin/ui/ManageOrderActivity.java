package com.example.btl_nhom3.feature_admin.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.adapter.OrderAdapter;
import com.example.btl_nhom3.feature_admin.model.Order;
import com.example.btl_nhom3.feature_admin.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;

public class ManageOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminRepository repository;
    ImageView btnFilter;
    List<Order> fullList;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);

        recyclerView = findViewById(R.id.recyclerViewOrder);
        repository = new AdminRepository(this);
        btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnFilter);

            popup.getMenu().add("Tất cả");
            popup.getMenu().add("Đang xử lý");
            popup.getMenu().add("Đang giao");
            popup.getMenu().add("Hoàn thành");
            popup.getMenu().add("Đã huỷ");

            popup.setOnMenuItemClickListener(item -> {

                String selected = item.getTitle().toString();

                List<Order> filtered = new ArrayList<>();

                if (selected.equals("Tất cả")) {
                    filtered = fullList;
                } else {
                    for (Order o : fullList) {
                        if (o.getStatus().equals(selected)) {
                            filtered.add(o);
                        }
                    }
                }

                adapter = new OrderAdapter(this, filtered);
                recyclerView.setAdapter(adapter);

                return true;
            });

            popup.show();
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    private void loadData() {
        fullList = repository.getAllOrders();
        adapter = new OrderAdapter(this, fullList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
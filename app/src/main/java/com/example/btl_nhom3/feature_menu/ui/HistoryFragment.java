package com.example.btl_nhom3.feature_menu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.adapter.OrderAdapter;
import com.example.btl_nhom3.feature_order.model.Order;
import com.example.btl_nhom3.feature_order.repository.OrderRepository;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView rvOrderHistory;
    private OrderAdapter adapter;
    private OrderRepository orderRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        rvOrderHistory = view.findViewById(R.id.rvOrderHistory);
        rvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        orderRepository = new OrderRepository(getContext());

        // Giả sử user_id đang đăng nhập là 1 (bạn cần thay thế bằng id thật từ SharedPreferences/Session)
        int currentUserId = 1;
        loadOrderHistory(currentUserId);

        return view;
    }

    private void loadOrderHistory(int userId) {
        List<Order> orders = orderRepository.getOrdersByUserId(userId);
        adapter = new OrderAdapter(orders);
        rvOrderHistory.setAdapter(adapter);
    }
}

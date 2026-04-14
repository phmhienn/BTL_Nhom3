package com.example.btl_nhom3.feature_order.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.btl_nhom3.feature_order.model.Order;
import com.example.btl_nhom3.feature_order.repository.OrderRepository;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository repository;
    private MutableLiveData<List<Order>> orderList = new MutableLiveData<>();

    public OrderViewModel(Application app) {
        super(app);
        repository = new OrderRepository(app);
    }

    public MutableLiveData<List<Order>> getOrderList() { return orderList; }

    // Trong OrderViewModel.java
    public void loadOrders(String username) {
        // 1. Clear dữ liệu cũ trước khi load mới để tránh lag dữ liệu user cũ
        orderList.setValue(null);

        // 2. Gọi Repo lấy đúng dữ liệu theo username
        List<Order> orders = repository.getOrdersByUsername(username);

        // 3. Cập nhật lại LiveData
        orderList.setValue(orders);
    }
}
package com.example.btl_nhom3.feature_menu.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.adapter.OrderAdapter;
import com.example.btl_nhom3.feature_order.viewmodel.OrderViewModel;

public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saved) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView rc = v.findViewById(R.id.recycler_history);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderViewModel vm = new ViewModelProvider(this).get(OrderViewModel.class);
        String user = getActivity().getSharedPreferences("UserFile", Context.MODE_PRIVATE).getString("username", "user");

        vm.getOrderList().observe(getViewLifecycleOwner(), orders -> rc.setAdapter(new OrderAdapter(orders)));
        vm.loadOrders(user);
        return v;
    }
}
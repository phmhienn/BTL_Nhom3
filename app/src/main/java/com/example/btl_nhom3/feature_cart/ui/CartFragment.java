package com.example.btl_nhom3.feature_cart.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.adapter.CartAdapter;
import com.example.btl_nhom3.feature_cart.model.CartItem;
import com.example.btl_nhom3.feature_cart.viewmodel.CartViewModel;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView txtTotal;
    private Button btnCheckout;

    private CartAdapter adapter;
    private CartViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCart = view.findViewById(R.id.rvCart);
        txtTotal = view.findViewById(R.id.txtTotal);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new CartAdapter(null);
        rvCart.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        observeViewModel();
        viewModel.loadCart();

        adapter.setOnCartItemActionListener(new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onIncrease(CartItem item) {
                viewModel.increaseQuantity(item);
            }

            @Override
            public void onDecrease(CartItem item) {
                viewModel.decreaseQuantity(item);
            }

            @Override
            public void onRemove(CartItem item) {
                viewModel.removeItem(item);
            }
        });

        btnCheckout.setText("Đặt hàng");
        // Keep button ready in UI only; order flow will be wired later.
        btnCheckout.setOnClickListener(v -> {
        });
    }

    private void observeViewModel() {
        viewModel.getCartItems().observe(getViewLifecycleOwner(), items -> adapter.submitList(items));

        viewModel.getTotal().observe(getViewLifecycleOwner(), total -> {
            int currentTotal = total == null ? 0 : total;
            txtTotal.setText("Tổng: " + currentTotal + "đ");
        });

        viewModel.getCanCheckout().observe(getViewLifecycleOwner(), canCheckout -> {
            boolean enabled = canCheckout != null && canCheckout;
            btnCheckout.setEnabled(enabled);
            if (!enabled) {
                txtTotal.setText("Giỏ hàng đang trống");
            }
        });
    }
}


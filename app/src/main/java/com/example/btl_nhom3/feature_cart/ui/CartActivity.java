package com.example.btl_nhom3.feature_cart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.adapter.CartAdapter;
import com.example.btl_nhom3.feature_cart.model.CartItem;
import com.example.btl_nhom3.feature_cart.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_ITEM_COUNT = "extra_item_count";

    RecyclerView rvCart;
    TextView txtTotal;
    Button btnCheckout;

    CartAdapter adapter;
    CartViewModel viewModel;
    List<CartItem> currentItems;
    int currentTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        txtTotal = findViewById(R.id.txtTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

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

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra(EXTRA_TOTAL, currentTotal);
            intent.putExtra(EXTRA_ITEM_COUNT, currentItems == null ? 0 : currentItems.size());
            startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getCartItems().observe(this, items -> {
            currentItems = items;
            adapter.submitList(items);
        });

        viewModel.getTotal().observe(this, total -> {
            currentTotal = total == null ? 0 : total;
            txtTotal.setText("Tổng: " + currentTotal + "đ");
        });

        viewModel.getCanCheckout().observe(this, canCheckout -> {
            boolean enabled = canCheckout != null && canCheckout;
            btnCheckout.setEnabled(enabled);
            if (!enabled) {
                txtTotal.setText("Giỏ hàng đang trống");
            }
        });
    }
}
package com.example.btl_nhom3.feature_cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartItem> list;

    public interface OnCartItemActionListener {
        void onIncrease(CartItem item);
        void onDecrease(CartItem item);
        void onRemove(CartItem item);
    }

    private OnCartItemActionListener actionListener;

    public CartAdapter(List<CartItem> list) {
        this.list = list;
    }

    public void setOnCartItemActionListener(OnCartItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void submitList(List<CartItem> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = list.get(position);

        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(item.getPrice() + "đ");
        holder.txtQty.setText("SL: " + item.getQuantity());

        holder.btnIncrease.setEnabled(item.getQuantity() < 100);

        holder.btnIncrease.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onIncrease(item);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDecrease(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice, txtQty;
        Button btnIncrease, btnDecrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQty = itemView.findViewById(R.id.txtQty);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
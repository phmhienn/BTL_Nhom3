package com.example.btl_nhom3.feature_cart.adapter;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static final int MIN_QUANTITY = 0;
    private static final int MAX_QUANTITY = 100;

    private List<CartItem> list;

    public interface OnCartItemActionListener {
        void onIncrease(CartItem item);
        void onDecrease(CartItem item);
        void onRemove(CartItem item);
        void onSetQuantity(CartItem item, int quantity);
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
        holder.edtQty.setText(String.valueOf(item.getQuantity()));

        holder.btnDecrease.setEnabled(item.getQuantity() > MIN_QUANTITY);
        holder.btnIncrease.setEnabled(item.getQuantity() < MAX_QUANTITY);

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

        holder.btnDelete.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onRemove(item);
            }
        });

        holder.edtQty.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                commitQuantity(holder.edtQty, item);
            }
        });

        holder.edtQty.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_NEXT
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN)) {
                commitQuantity(holder.edtQty, item);
                holder.edtQty.clearFocus();
                return true;
            }
            return false;
        });
    }

    private void commitQuantity(EditText edtQty, CartItem item) {
        String raw = edtQty.getText().toString().trim();
        if (raw.isEmpty()) {
            edtQty.setText(String.valueOf(item.getQuantity()));
            return;
        }

        try {
            int parsed = Integer.parseInt(raw);
            int safeQuantity = Math.max(MIN_QUANTITY, Math.min(MAX_QUANTITY, parsed));
            edtQty.setText(String.valueOf(safeQuantity));

            if (safeQuantity != item.getQuantity() && actionListener != null) {
                actionListener.onSetQuantity(item, safeQuantity);
            }
        } catch (NumberFormatException e) {
            edtQty.setText(String.valueOf(item.getQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice;
        EditText edtQty;
        Button btnIncrease, btnDecrease, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtQty = itemView.findViewById(R.id.edtQty);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
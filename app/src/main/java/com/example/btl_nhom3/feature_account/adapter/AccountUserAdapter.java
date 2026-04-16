package com.example.btl_nhom3.feature_account.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_account.model.AccountUser;

import java.util.ArrayList;
import java.util.List;

public class AccountUserAdapter extends RecyclerView.Adapter<AccountUserAdapter.AccountUserViewHolder> {

    public interface AccountActionListener {
        void onChangeRole(AccountUser user);
        void onDelete(AccountUser user);
    }

    private final List<AccountUser> users = new ArrayList<>();
    private final AccountActionListener listener;

    public AccountUserAdapter(AccountActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<AccountUser> newData) {
        users.clear();
        if (newData != null) {
            users.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_user, parent, false);
        return new AccountUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountUserViewHolder holder, int position) {
        AccountUser user = users.get(position);
        holder.txtUserId.setText("#" + user.getId());
        holder.txtUsername.setText("Tài khoản: " + safe(user.getUsername()));
        holder.txtFullName.setText("Họ tên: " + safe(user.getFullName()));
        holder.txtPhone.setText("SĐT: " + safe(user.getPhone()));
        holder.txtAddress.setText("Địa chỉ: " + safe(user.getAddress()));
        holder.txtRole.setText("Quyền: " + (user.isAdmin() ? "Admin" : "User"));

        holder.txtRole.setOnClickListener(v -> listener.onChangeRole(user));
        holder.btnDeleteUser.setOnClickListener(v -> listener.onDelete(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    static class AccountUserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserId;
        TextView txtUsername;
        TextView txtFullName;
        TextView txtPhone;
        TextView txtAddress;
        TextView txtRole;
        Button btnDeleteUser;

        AccountUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.txtUserId);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtRole = itemView.findViewById(R.id.txtRole);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}


package com.example.btl_nhom3.feature_admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.*;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.model.Order;
import com.example.btl_nhom3.feature_admin.repository.AdminRepository;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> list;
    private AdminRepository repository;

    public OrderAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
        repository = new AdminRepository(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtUser, txtAddress, txtItems, txtTotal,txtDate, txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtItems = itemView.findViewById(R.id.txtItems);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = list.get(position);

        holder.txtOrderId.setText("Đơn #" + order.getId());
        holder.txtUser.setText("Khách: " + order.getFullName());
        holder.txtAddress.setText("Địa chỉ: " + order.getAddress());

        // ================= HIỂN THỊ MÓN =================
        List<String> items = repository.getOrderItems(order.getId());
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(items.get(i))
                    .append("\n");
        }

        holder.txtItems.setText(builder.toString());

        // ================= TỔNG TIỀN =================
        int total = repository.getTotalByOrder(order.getId());
        holder.txtTotal.setText("Tổng: " + total + " VNĐ");
        holder.txtDate.setText("Ngày đặt: " + order.getCreatedAt());

        // ================= TRẠNG THÁI =================
        holder.txtStatus.setText("Trạng thái: " + order.getStatus());

        // đổi màu cho đẹp
        switch (order.getStatus()) {
            case "Hoàn thành":
                holder.txtStatus.setTextColor(0xFF4CAF50); // xanh
                break;
            case "Đã huỷ":
                holder.txtStatus.setTextColor(0xFFF44336); // đỏ
                break;
            case "Đang giao":
                holder.txtStatus.setTextColor(0xFF2196F3); // xanh dương
                break;
            default:
                holder.txtStatus.setTextColor(0xFFFF9800); // cam
        }

        // ================= POPUP MENU =================
        holder.txtStatus.setText("Trạng thái: " + order.getStatus());

// ❌ nếu đã hoàn thành hoặc huỷ → không cho click
        if (order.getStatus().equals("Hoàn thành") || order.getStatus().equals("Đã huỷ")) {

            holder.txtStatus.setEnabled(false); // không bấm được
            holder.txtStatus.setAlpha(0.6f);    // làm mờ cho dễ nhận biết

        } else {

            holder.txtStatus.setEnabled(true);
            holder.txtStatus.setAlpha(1f);

            holder.txtStatus.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(context, holder.txtStatus);

                popup.getMenu().add("Đang xử lý");
                popup.getMenu().add("Đang giao");
                popup.getMenu().add("Hoàn thành");
                popup.getMenu().add("Đã huỷ");

                popup.setOnMenuItemClickListener(item -> {
                    String newStatus = item.getTitle().toString();

                    repository.updateOrderStatus(order.getId(), newStatus);
                    order.setStatus(newStatus);

                    notifyItemChanged(position);

                    return true;
                });

                popup.show();
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
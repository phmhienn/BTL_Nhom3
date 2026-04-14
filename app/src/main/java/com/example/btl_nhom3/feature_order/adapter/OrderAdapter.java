package com.example.btl_nhom3.feature_order.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_order.model.Order;
import com.example.btl_nhom3.feature_order.ui.OrderStatusActivity;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {
    private List<Order> list;

    public OrderAdapter(List<Order> list) { this.list = list; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_order, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Order o = list.get(pos);
        h.id.setText("Đơn hàng #" + o.getId());
        h.status.setText(o.getStatus());
        h.date.setText(o.getCreatedAt());
        h.total.setText(String.format("%, d VNĐ", o.getTotalPrice()));

        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), OrderStatusActivity.class);
            i.putExtra("ID", o.getId());
            i.putExtra("STATUS", o.getStatus());
            i.putExtra("DATE", o.getCreatedAt());
            i.putExtra("ADDR", o.getAddress()); // QUAN TRỌNG: Phải gửi cái này
            i.putExtra("TOTAL", o.getTotalPrice());
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    class VH extends RecyclerView.ViewHolder {
        TextView id, status, date, total;
        VH(View v) {
            super(v);
            id = v.findViewById(R.id.tvOrderId);
            status = v.findViewById(R.id.tvStatus);
            date = v.findViewById(R.id.tvDate);
            total = v.findViewById(R.id.tvTotal);
        }
    }
}

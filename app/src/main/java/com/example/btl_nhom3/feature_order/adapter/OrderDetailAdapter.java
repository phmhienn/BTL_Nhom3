package com.example.btl_nhom3.feature_order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import java.util.List;
import java.util.Map;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private List<Map<String, Object>> itemList;

    public OrderDetailAdapter(List<Map<String, Object>> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> item = itemList.get(position);

        // Lấy dữ liệu từ Map theo đúng Key mà bạn đã put trong OrderRepository
        String name = (String) item.get("name");
        int quantity = (int) item.get("quantity");
        int price = (int) item.get("price");

        holder.tvName.setText(name);
        holder.tvQty.setText("x" + quantity);
        holder.tvPrice.setText(String.format("%, d đ", price));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvQty = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
        }
    }
}

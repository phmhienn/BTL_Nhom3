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

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.VH> {

    // Danh sách chứa thông tin món ăn: tên, số lượng, giá
    private List<Map<String, Object>> list;

    // Constructor: Nhận dữ liệu từ Activity truyền vào
    public OrderDetailAdapter(List<Map<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ánh xạ file XML item_order_detail
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Map<String, Object> item = list.get(position);

        // Đổ dữ liệu vào các TextView
        holder.tvName.setText((String) item.get("name"));
        holder.tvQty.setText("x" + item.get("quantity"));

        // Định dạng tiền tệ (ví dụ: 50,000 VNĐ)
        int price = (int) item.get("price");
        holder.tvPrice.setText(String.format("%, d VNĐ", price));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    // ViewHolder để giữ các View trong item
    class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvPrice;

        public VH(@NonNull View v) {
            super(v);
            tvName = v.findViewById(R.id.tvFoodName);
            tvQty = v.findViewById(R.id.tvQuantity);
            tvPrice = v.findViewById(R.id.tvPrice);
        }
    }
}
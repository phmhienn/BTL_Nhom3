package com.example.btl_nhom3.feature_menu.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_menu.model.Food;
import com.example.btl_nhom3.feature_menu.ui.FoodDetailActivity;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    List<Food> list;

    public FoodAdapter(List<Food> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Food food = list.get(position);

            holder.txtName.setText(food.getName());
            holder.txtPrice.setText(food.getPrice() + "đ");
            holder.txtDesc.setText(food.getDescription());
            holder.imgFood.setImageResource(food.getImage());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);

                intent.putExtra("name", food.getName());
                intent.putExtra("price", food.getPrice());
                intent.putExtra("image", food.getImage());
            intent.putExtra("desc", food.getDescription());
            intent.putExtra("quantity", food.getQuantity());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtName, txtPrice, txtDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }

}
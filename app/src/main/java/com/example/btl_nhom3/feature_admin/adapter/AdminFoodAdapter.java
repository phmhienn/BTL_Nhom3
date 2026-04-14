package com.example.btl_nhom3.feature_admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.model.Food;
import com.example.btl_nhom3.feature_admin.repository.AdminRepository;
import com.example.btl_nhom3.feature_admin.ui.AddFoodActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.ViewHolder> {

    private Context context;
    private List<Food> list;
    private List<Food> originalList;
    private AdminRepository repository;
    ImageView imgFood;

    public AdminFoodAdapter(Context context, List<Food> list) {
        this.context = context;
        this.list = list;
        this.originalList = new ArrayList<>(list);
        repository = new AdminRepository(context);
    }
    public String removeAccent(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    public void filter(String keyword) {
        list.clear();

        if (keyword.isEmpty()) {
            list.addAll(originalList);
        } else {
            String keywordNoAccent = removeAccent(keyword.toLowerCase().trim());

            for (Food food : originalList) {
                String nameNoAccent = removeAccent(food.getName().toLowerCase());

                if (nameNoAccent.contains(keywordNoAccent)) {
                    list.add(food);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice,txtQuantity, btnDelete,btnEdit;ImageView imgFood;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_admin, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = list.get(position);

        holder.txtName.setText(food.getName());
        holder.txtPrice.setText(food.getPrice() + " VNĐ");
        holder.txtQuantity.setText("Số lượng: " + food.getQuantity());

        if (food.getImage() != null && !food.getImage().isEmpty()) {
            holder.imgFood.setImageURI(Uri.parse(food.getImage()));
        }

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xoá món")
                    .setMessage("Bạn có chắc muốn xoá?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        repository.deleteFood(food.getId());
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddFoodActivity.class);

            intent.putExtra("id", food.getId());
            intent.putExtra("image", food.getImage());
            intent.putExtra("name", food.getName());
            intent.putExtra("price", food.getPrice());
            intent.putExtra("quantity", food.getQuantity());
            intent.putExtra("desc", food.getDescription());

            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
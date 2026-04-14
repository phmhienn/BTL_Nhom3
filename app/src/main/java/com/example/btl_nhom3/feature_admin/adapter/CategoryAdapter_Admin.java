package com.example.btl_nhom3.feature_admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_admin.repository.CategoryRepository;
import com.example.btl_nhom3.feature_menu.model.Category;

import java.util.List;

public class CategoryAdapter_Admin extends RecyclerView.Adapter<CategoryAdapter_Admin.ViewHolder> {

    List<Category> list;
    Context context;
    CategoryRepository repo;

    public CategoryAdapter_Admin(Context context, List<Category> list) {
        this.context = context;
        this.list = list;
        repo = new CategoryRepository(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_category_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category c = list.get(position);
        holder.txtName.setText(c.getName());

        // XÓA
        holder.btnDelete.setOnClickListener(v -> {
            repo.delete(c.getId());
            list.remove(position);
            notifyDataSetChanged();
        });

        // SỬA
        holder.itemView.setOnClickListener(v -> {
            EditText edt = new EditText(context);
            edt.setText(c.getName());

            new AlertDialog.Builder(context)
                    .setTitle("Sửa danh mục")
                    .setView(edt)
                    .setPositiveButton("OK", (dialog, which) -> {
                        String newName = edt.getText().toString();
                        repo.update(new Category(c.getId(), newName, c.getIcon()));
                        list.get(position).setName(newName);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

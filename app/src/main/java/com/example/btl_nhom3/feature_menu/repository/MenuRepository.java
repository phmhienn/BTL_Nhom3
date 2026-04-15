package com.example.btl_nhom3.feature_menu.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_menu.model.Category;
import com.example.btl_nhom3.feature_menu.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    private Database dbHelper;
    private Context context;

    public MenuRepository(Context context) {
        this.context = context;
        this.dbHelper = new Database(context);
    }

    // Lấy danh sách Category từ DB
    public List<Category> getCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int icon = context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndexOrThrow("icon")), "drawable", context.getPackageName());

                list.add(new Category(id, name, icon));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Lấy danh sách Food từ DB
    public List<Food> getFoods() {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM foods", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String imgName = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));

                // Chuyển tên ảnh (string) thành Resource ID
                int imageResId = 0;
                try {
                    imageResId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                } catch (Exception e) {
                    imageResId = 0;
                }

                if (imageResId == 0) {
                    imageResId = R.drawable.ic_launcher_background; // Ảnh mặc định nếu không tìm thấy
                }

                list.add(new Food(id, name, price, categoryId, imageResId, desc, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
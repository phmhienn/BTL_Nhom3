package com.example.btl_nhom3.feature_admin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_admin.model.Food;

import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    private Database dbHelper;

    public AdminRepository(Context context) {
        dbHelper = new Database(context);
    }

    // ================= FOOD =================

    public long insertFood(Food food) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", food.getName());
        values.put("price", food.getPrice());
        values.put("description", food.getDescription());
        values.put("image", food.getImage());
        values.put("quantity", food.getQuantity());
        values.put("category_id", food.getCategoryId());

        return db.insert("foods", null, values);
    }

    public List<Food> getAllFood() {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM foods", null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                food.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                food.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                food.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                food.setImage(cursor.getString(cursor.getColumnIndexOrThrow("image")));
                food.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                food.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));

                list.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public int deleteFood(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("foods", "id=?", new String[]{String.valueOf(id)});
    }

    public int updateFood(Food food) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", food.getName());
        values.put("price", food.getPrice());
        values.put("description", food.getDescription());
        values.put("image", food.getImage());
        values.put("quantity", food.getQuantity());
        values.put("category_id", food.getCategoryId());

        return db.update("foods", values, "id=?", new String[]{String.valueOf(food.getId())});
    }
}
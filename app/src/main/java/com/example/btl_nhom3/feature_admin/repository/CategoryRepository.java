package com.example.btl_nhom3.feature_admin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_menu.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    Database dbHelper;

    public CategoryRepository(Context context) {
        dbHelper = new Database(context);
    }

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);

        while (cursor.moveToNext()) {
            list.add(new Category(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }

        cursor.close();
        return list;
    }

    public void insert(Category c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", c.getId());
        values.put("name", c.getName());
        values.put("icon", c.getIcon());

        db.insert("categories", null, values);
    }

    public void update(Category c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", c.getName());
        values.put("icon", c.getIcon());

        db.update("categories", values, "id=?", new String[]{String.valueOf(c.getId())});
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("categories", "id=?", new String[]{String.valueOf(id)});
    }
}

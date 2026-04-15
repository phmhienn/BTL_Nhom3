package com.example.btl_nhom3.feature_order.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_order.model.Order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderRepository {
    private Database dbHelper;

    public OrderRepository(Context context) {
        dbHelper = new Database(context);
    }

    public long checkout(String username, int total, String address, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("total_price", total);
            values.put("status", "Đang xử lý");
            values.put("address", address);
            values.put("phone", phone);
            values.put("created_at", new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date()));

            long orderId = db.insert("orders", null, values);

            // Chuyển dữ liệu từ cart sang order_items dựa trên user_id
            String sql = "INSERT INTO order_items (order_id, food_id, quantity, price) " +
                    "SELECT " + orderId + ", c.food_id, c.quantity, f.price " +
                    "FROM cart c INNER JOIN foods f ON c.food_id = f.id " +
                    "WHERE c.user_id = (SELECT id FROM users WHERE username = ?)";
            db.execSQL(sql, new String[]{username});

            // Xóa giỏ hàng
            db.execSQL("DELETE FROM cart WHERE user_id = (SELECT id FROM users WHERE username = ?)", new String[]{username});

            db.setTransactionSuccessful();
            return orderId;
        } finally {
            db.endTransaction();
        }
    }

    // Trong file OrderRepository.java
    // Trong file OrderRepository.java
    public List<Order> getOrdersByUsername(String username) {
        List<Order> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Sử dụng username để lọc chính xác
        String sql = "SELECT * FROM orders WHERE username = ? ORDER BY id DESC";
        Cursor c = db.rawQuery(sql, new String[]{username});

        while (c.moveToNext()) {
            // Sử dụng getColumnIndexOrThrow để tránh lỗi sai thứ tự cột
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String user = c.getString(c.getColumnIndexOrThrow("username"));
            int price = c.getInt(c.getColumnIndexOrThrow("total_price"));
            String status = c.getString(c.getColumnIndexOrThrow("status"));
            String date = c.getString(c.getColumnIndexOrThrow("created_at"));
            String addr = c.getString(c.getColumnIndexOrThrow("address"));
            String phone = c.getString(c.getColumnIndexOrThrow("phone"));

            list.add(new Order(id, user, price, status, date, addr, phone));
        }
        c.close();
        return list;
    }

    public List<Map<String, Object>> getOrderDetails(int orderId) {
        List<Map<String, Object>> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Câu lệnh SQL phải JOIN bảng món ăn để lấy tên
        String sql = "SELECT f.name, oi.quantity, oi.price " +
                "FROM order_items oi " +
                "JOIN foods f ON oi.food_id = f.id " +
                "WHERE oi.order_id = ?";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(orderId)});
        if (c != null) {
            while (c.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", c.getString(0));
                map.put("quantity", c.getInt(1));
                map.put("price", c.getInt(2));
                list.add(map);
            }
            c.close();
        }
        return list;
    }
}
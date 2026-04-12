package com.example.btl_nhom3.feature_order.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_order.model.Order;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;


public class OrderRepository {
    private Database dbHelper;

    public OrderRepository(Context context) {
        dbHelper = new Database(context);
    }

    // Lấy danh sách đơn hàng theo user_id
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(0),  // id
                        cursor.getInt(1),  // user_id
                        cursor.getInt(2),  // total_price
                        cursor.getString(3), // status
                        cursor.getString(4), // created_at
                        cursor.getString(5)  // address
                );
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }

    // Thêm hàm này vào class OrderRepository của bạn
    public long placeOrder(int userId, int totalPrice, String address, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long orderId = -1;

        db.beginTransaction(); // Dùng transaction để đảm bảo an toàn dữ liệu
        try {
            // 1. Chèn vào bảng orders
            ContentValues orderValues = new ContentValues();
            orderValues.put("user_id", userId);
            orderValues.put("total_price", totalPrice);
            orderValues.put("status", "Đang xử lý");
            orderValues.put("created_at", date);
            orderValues.put("address", address);

            orderId = db.insert("orders", null, orderValues);

            if (orderId != -1) {
                // 2. Chép dữ liệu từ bảng cart sang order_items (Dùng SQL cho nhanh)
                String copySql = "INSERT INTO order_items (order_id, food_id, quantity, price) " +
                        "SELECT " + orderId + ", cart.food_id, cart.quantity, foods.price " +
                        "FROM cart " +
                        "JOIN foods ON cart.food_id = foods.id " +
                        "WHERE cart.user_id = " + userId;
                db.execSQL(copySql);

                // 3. Xóa giỏ hàng sau khi đặt xong
                db.delete("cart", "user_id = ?", new String[]{String.valueOf(userId)});

                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return orderId;
    }
}

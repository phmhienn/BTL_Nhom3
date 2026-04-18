package com.example.btl_nhom3.feature_cart.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private static final int MIN_QUANTITY = 0;
    private static final int MAX_QUANTITY = 100;
    private static final String PREF_USER = "USER";
    private static final String KEY_USER_ID = "user_id";
    private static CartRepository instance;
    private Database dbHelper;

    private CartRepository() {
    }

    public static synchronized CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    private synchronized void ensureDb(Context context) {
        if (dbHelper == null && context != null) {
            dbHelper = new Database(context.getApplicationContext());
        }
    }

    private int getCurrentUserId(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
                .getInt(KEY_USER_ID, 0);
    }

    public void addItem(Context context, int itemId, String name, int price) {
        ensureDb(context);
        if (dbHelper == null || itemId <= 0) {
            return;
        }

        int userId = getCurrentUserId(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT quantity FROM cart WHERE user_id = ? AND food_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(itemId)}
        );

        int nextQuantity = 1;
        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = true;
            nextQuantity = Math.min(MAX_QUANTITY, cursor.getInt(0) + 1);
        }
        cursor.close();

        if (exists) {
            ContentValues updateValues = new ContentValues();
            updateValues.put("quantity", nextQuantity);
            db.update("cart", updateValues, "user_id = ? AND food_id = ?",
                    new String[]{String.valueOf(userId), String.valueOf(itemId)});
            return;
        }

        ContentValues insertValues = new ContentValues();
        insertValues.put("user_id", userId);
        insertValues.put("food_id", itemId);
        insertValues.put("quantity", 1);
        db.insert("cart", null, insertValues);
    }

    public void addItem(int itemId, String name, int price) {
        // Backward-compatible API; DB persistence needs context.
    }

    public List<CartItem> getCart(Context context) {
        ensureDb(context);
        List<CartItem> cartItems = new ArrayList<>();
        if (dbHelper == null) {
            return cartItems;
        }

        int userId = getCurrentUserId(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT c.food_id, f.name, f.price, c.quantity " +
                "FROM cart c JOIN foods f ON c.food_id = f.id WHERE c.user_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            int foodId = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int quantity = cursor.getInt(3);
            cartItems.add(new CartItem(foodId, name, price, quantity));
        }
        cursor.close();
        return cartItems;
    }

    public List<CartItem> getCart() {
        return new ArrayList<>();
    }

    public void updateQuantity(Context context, int itemId, int quantity) {
        ensureDb(context);
        if (dbHelper == null) {
            return;
        }

        int safeQuantity = Math.max(MIN_QUANTITY, Math.min(MAX_QUANTITY, quantity));
        int userId = getCurrentUserId(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (safeQuantity <= 0) {
            db.delete("cart", "user_id = ? AND food_id = ?",
                    new String[]{String.valueOf(userId), String.valueOf(itemId)});
            return;
        }

        ContentValues values = new ContentValues();
        values.put("quantity", safeQuantity);
        db.update("cart", values, "user_id = ? AND food_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(itemId)});
    }

    public void updateQuantity(int itemId, int quantity) {
        // Backward-compatible API; DB persistence needs context.
    }

    public void removeItem(Context context, int itemId) {
        updateQuantity(context, itemId, 0);
    }

    public void removeItem(int itemId) {
        // Backward-compatible API; DB persistence needs context.
    }

    public int calculateTotal(Context context) {
        int total = 0;
        List<CartItem> cartItems = getCart(context);
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int calculateTotal() {
        int total = 0;
        return total;
    }

    public void clearCart(Context context) {
        ensureDb(context);
        if (dbHelper == null) {
            return;
        }

        int userId = getCurrentUserId(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "user_id = ?", new String[]{String.valueOf(userId)});
    }

    public void clearCart() {
        // Backward-compatible API; DB persistence needs context.
    }
}
package com.example.btl_nhom3.feature_account.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_account.model.AccountUser;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private final Database dbHelper;

    public AccountRepository(Context context) {
        dbHelper = new Database(context.getApplicationContext());
    }

    public List<AccountUser> searchUsers(String keyword) {
        List<AccountUser> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;
        if (keyword == null || keyword.trim().isEmpty()) {
            cursor = db.rawQuery(
                    "SELECT id, username, fullname, phone, address, role FROM users ORDER BY id DESC",
                    null
            );
        } else {
            String q = "%" + keyword.trim() + "%";
            cursor = db.rawQuery(
                    "SELECT id, username, fullname, phone, address, role FROM users " +
                            "WHERE username LIKE ? OR fullname LIKE ? OR phone LIKE ? ORDER BY id DESC",
                    new String[]{q, q, q}
            );
        }

        if (cursor.moveToFirst()) {
            do {
                users.add(mapUser(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return users;
    }

    public boolean updateRole(int userId, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("role", role);
        return db.update("users", values, "id=?", new String[]{String.valueOf(userId)}) > 0;
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("users", "id=?", new String[]{String.valueOf(userId)}) > 0;
    }

    public int countAdmins() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM users WHERE lower(role)='admin'", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    private AccountUser mapUser(Cursor cursor) {
        AccountUser user = new AccountUser();
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
        user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow("fullname")));
        user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
        user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
        return user;
    }
}


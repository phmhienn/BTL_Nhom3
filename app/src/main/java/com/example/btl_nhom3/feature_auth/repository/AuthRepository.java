package com.example.btl_nhom3.feature_auth.repository;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_auth.model.User;

public class AuthRepository {
	private final Database databaseHelper;

	public AuthRepository(Context context) {
		this.databaseHelper = new Database(context.getApplicationContext());
	}

	public User login(String username, String password) {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT id, username, fullname, phone, address, role " +
						"FROM users WHERE username = ? AND password = ? LIMIT 1",
				new String[]{username, password}
		);

		User user = null;
		if (cursor.moveToFirst()) {
			user = new User(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5)
			);
		}

		cursor.close();
		db.close();
		return user;
	}

	public User getUserById(int userId) {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT id, username, fullname, phone, address, role FROM users WHERE id = ? LIMIT 1",
				new String[]{String.valueOf(userId)}
		);

		User user = null;
		if (cursor.moveToFirst()) {
			user = new User(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getString(3),
					cursor.getString(4),
					cursor.getString(5)
			);
		}

		cursor.close();
		db.close();
		return user;
	}

	public boolean isUsernameExists(String username) {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT id FROM users WHERE username = ? LIMIT 1",
				new String[]{username}
		);

		boolean exists = cursor.moveToFirst();
		cursor.close();
		db.close();
		return exists;
	}

	public User register(String username, String password, String fullName, String phone, String address) {
		String role = "user";
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("username", username);
		values.put("password", password);
		values.put("fullname", fullName);
		values.put("phone", phone);
		values.put("address", address);
		values.put("role", role);

		long insertId = db.insert("users", null, values);
		db.close();

		if (insertId <= 0) {
			return null;
		}

		return new User((int) insertId, username, fullName, phone, address, role);
	}
}

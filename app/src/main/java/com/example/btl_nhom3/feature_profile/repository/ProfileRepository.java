package com.example.btl_nhom3.feature_profile.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.repository.AuthRepository;
import com.example.btl_nhom3.feature_profile.model.ProfileUser;

public class ProfileRepository {

    private final AuthRepository authRepository;
	private final Database database;

    public ProfileRepository(Context context) {
        this.authRepository = new AuthRepository(context);
		this.database = new Database(context.getApplicationContext());
    }

    public ProfileUser getProfileByUserId(int userId) {
        User user = authRepository.getUserById(userId);
        if (user == null) {
            return null;
        }

        return new ProfileUser(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPhone(),
                user.getAddress()
        );
    }

  public ProfileUser updateProfile(int userId, String fullName, String phone, String address) {
		SQLiteDatabase db = database.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("fullname", fullName);
		values.put("phone", phone);
		values.put("address", address);

		int affectedRows = db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});
		db.close();

		if (affectedRows <= 0) {
			return null;
		}

		return getProfileByUserId(userId);
  }

  public boolean changePassword(int userId, String currentPassword, String newPassword) {
    SQLiteDatabase db = database.getWritableDatabase();

    Cursor cursor = db.rawQuery(
        "SELECT id FROM users WHERE id = ? AND password = ? LIMIT 1",
        new String[]{String.valueOf(userId), currentPassword}
    );

    boolean matched = cursor.moveToFirst();
    cursor.close();

    if (!matched) {
      db.close();
      return false;
    }

    ContentValues values = new ContentValues();
    values.put("password", newPassword);

    int affectedRows = db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});
    db.close();
    return affectedRows > 0;
  }
}


package com.example.btl_nhom3.feature_auth.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.repository.AuthRepository;

public class ProfileViewModel extends AndroidViewModel {

	private static final String PREF_USER = "USER";
	private static final String KEY_USER_ID = "user_id";

	private final AuthRepository repository;
	private final MutableLiveData<User> user = new MutableLiveData<>();

	public ProfileViewModel(@NonNull Application application) {
		super(application);
		repository = new AuthRepository(application);
	}

	public LiveData<User> getUser() {
		return user;
	}

	public void loadCurrentUser() {
		int userId = getApplication()
				.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
				.getInt(KEY_USER_ID, -1);

		if (userId <= 0) {
			user.setValue(null);
			return;
		}

		user.setValue(repository.getUserById(userId));
	}
}

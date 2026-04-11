package com.example.btl_nhom3.feature_auth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {

	private final AuthRepository repository;
	private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
	private final MutableLiveData<User> loginUser = new MutableLiveData<>();
	private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

	public AuthViewModel(@NonNull Application application) {
		super(application);
		repository = new AuthRepository(application);
	}

	public LiveData<Boolean> getLoading() {
		return loading;
	}

	public LiveData<User> getLoginUser() {
		return loginUser;
	}

	public LiveData<String> getErrorMessage() {
		return errorMessage;
	}

	public void login(String username, String password) {
		String safeUsername = username == null ? "" : username.trim();
		String safePassword = password == null ? "" : password.trim();

		if (safeUsername.isEmpty() || safePassword.isEmpty()) {
			errorMessage.setValue("Vui lòng nhập đầy đủ tài khoản và mật khẩu");
			return;
		}

		loading.setValue(true);
		User user = repository.login(safeUsername, safePassword);
		loading.setValue(false);

		if (user == null) {
			errorMessage.setValue("Sai tài khoản hoặc mật khẩu");
			return;
		}

		errorMessage.setValue("");
		loginUser.setValue(user);
	}
}

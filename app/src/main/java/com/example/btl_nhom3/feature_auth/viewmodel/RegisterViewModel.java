package com.example.btl_nhom3.feature_auth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.repository.AuthRepository;

public class RegisterViewModel extends AndroidViewModel {

    private final AuthRepository repository;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<User> registerUser = new MutableLiveData<>();
    private final MutableLiveData<String> registerMessage = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthRepository(application);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<User> getRegisterUser() {
        return registerUser;
    }

    public LiveData<String> getRegisterMessage() {
        return registerMessage;
    }

    public void register(String username, String password, String confirmPassword,
                         String fullName, String phone, String address) {
        String safeUsername = username == null ? "" : username.trim();
        String safePassword = password == null ? "" : password.trim();
        String safeConfirm = confirmPassword == null ? "" : confirmPassword.trim();
        String safeFullName = fullName == null ? "" : fullName.trim();
        String safePhone = phone == null ? "" : phone.trim();
        String safeAddress = address == null ? "" : address.trim();

        if (safeUsername.isEmpty() || safePassword.isEmpty() || safeConfirm.isEmpty()) {
            registerMessage.setValue("Vui lòng nhập tài khoản và mật khẩu");
            return;
        }

        if (!safePassword.equals(safeConfirm)) {
            registerMessage.setValue("Mật khẩu xác nhận không khớp");
            return;
        }

        if (repository.isUsernameExists(safeUsername)) {
            registerMessage.setValue("Tài khoản đã tồn tại");
            return;
        }

        loading.setValue(true);
        User user = repository.register(safeUsername, safePassword, safeFullName, safePhone, safeAddress);
        loading.setValue(false);

        if (user == null) {
            registerMessage.setValue("Đăng ký thất bại, vui lòng thử lại");
            return;
        }

        registerUser.setValue(user);
        registerMessage.setValue("Đăng ký thành công, vui lòng đăng nhập");
    }
}


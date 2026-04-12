package com.example.btl_nhom3.feature_profile.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.btl_nhom3.feature_profile.model.ProfileUser;
import com.example.btl_nhom3.feature_profile.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private static final String PREF_USER = "USER";
    private static final String KEY_USER_ID = "user_id";

    private final ProfileRepository repository;
    private final MutableLiveData<ProfileUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> actionMessage = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
    }

    public LiveData<ProfileUser> getUserLiveData() {
        return userLiveData;
    }

	public LiveData<String> getActionMessage() {
		return actionMessage;
	}

    public void loadProfile() {
        int userId = getApplication()
                .getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
                .getInt(KEY_USER_ID, -1);

        if (userId <= 0) {
            userLiveData.setValue(null);
            return;
        }

        userLiveData.setValue(repository.getProfileByUserId(userId));
    }

  public void updateProfile(String fullName, String phone, String address) {
    int userId = getCurrentUserId();
    if (userId <= 0) {
      actionMessage.setValue("Bạn chưa đăng nhập");
      return;
    }

    String safeFullName = safeText(fullName);
    String safePhone = safeText(phone);
    String safeAddress = safeText(address);
    if (safeFullName.isEmpty() || safePhone.isEmpty() || safeAddress.isEmpty()) {
      actionMessage.setValue("Vui lòng nhập đầy đủ thông tin");
      return;
    }

    ProfileUser updated = repository.updateProfile(userId, safeFullName, safePhone, safeAddress);
    if (updated == null) {
      actionMessage.setValue("Cập nhật thông tin thất bại");
      return;
    }

    userLiveData.setValue(updated);
    actionMessage.setValue("Cập nhật thông tin thành công");
  }

  public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
    int userId = getCurrentUserId();
    if (userId <= 0) {
      actionMessage.setValue("Bạn chưa đăng nhập");
      return;
    }

    String safeCurrentPassword = safeText(currentPassword);
    String safeNewPassword = safeText(newPassword);
    String safeConfirmPassword = safeText(confirmPassword);

    if (safeCurrentPassword.isEmpty() || safeNewPassword.isEmpty() || safeConfirmPassword.isEmpty()) {
      actionMessage.setValue("Vui lòng nhập đầy đủ mật khẩu");
      return;
    }

    if (safeNewPassword.length() < 6) {
      actionMessage.setValue("Mật khẩu mới tối thiểu 6 ký tự");
      return;
    }

    if (!safeNewPassword.equals(safeConfirmPassword)) {
      actionMessage.setValue("Mật khẩu xác nhận không khớp");
      return;
    }

    boolean changed = repository.changePassword(userId, safeCurrentPassword, safeNewPassword);
    actionMessage.setValue(changed ? "Đổi mật khẩu thành công" : "Mật khẩu hiện tại không đúng");
  }

  private int getCurrentUserId() {
    return getApplication()
        .getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
        .getInt(KEY_USER_ID, -1);
  }

  private String safeText(String value) {
    return value == null ? "" : value.trim();
  }
}


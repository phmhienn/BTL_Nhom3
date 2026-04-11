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

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
    }

    public LiveData<ProfileUser> getUserLiveData() {
        return userLiveData;
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
}


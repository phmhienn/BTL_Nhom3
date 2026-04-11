package com.example.btl_nhom3.feature_profile.repository;

import android.content.Context;

import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.repository.AuthRepository;
import com.example.btl_nhom3.feature_profile.model.ProfileUser;

public class ProfileRepository {

    private final AuthRepository authRepository;

    public ProfileRepository(Context context) {
        this.authRepository = new AuthRepository(context);
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
                user.getAddress(),
                user.getRole()
        );
    }
}


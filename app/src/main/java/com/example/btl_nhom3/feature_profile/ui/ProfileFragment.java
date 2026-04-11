package com.example.btl_nhom3.feature_profile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.btl_nhom3.feature_auth.ui.LoginActivity;
import com.example.btl_nhom3.feature_profile.model.ProfileUser;
import com.example.btl_nhom3.feature_profile.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private static final String PREF_USER = "USER";

    private ProfileViewModel viewModel;
    private TextView txtUsername;
    private TextView txtFullName;
    private TextView txtPhone;
    private TextView txtAddress;
    private TextView txtRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        LinearLayout root = new LinearLayout(requireContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        root.setPadding(dp(20), dp(20), dp(20), dp(20));

        TextView title = new TextView(requireContext());
        title.setText("Thong tin ca nhan");
        title.setTextSize(22);
        title.setGravity(Gravity.START);

        txtUsername = new TextView(requireContext());
        txtFullName = new TextView(requireContext());
        txtPhone = new TextView(requireContext());
        txtAddress = new TextView(requireContext());
        txtRole = new TextView(requireContext());

        txtUsername.setPadding(0, dp(12), 0, 0);
        txtFullName.setPadding(0, dp(8), 0, 0);
        txtPhone.setPadding(0, dp(8), 0, 0);
        txtAddress.setPadding(0, dp(8), 0, 0);
        txtRole.setPadding(0, dp(8), 0, 0);

        Button btnLogout = new Button(requireContext());
        btnLogout.setText("Dang xuat");
        btnLogout.setPadding(0, dp(16), 0, 0);

        root.addView(title);
        root.addView(txtUsername);
        root.addView(txtFullName);
        root.addView(txtPhone);
        root.addView(txtAddress);
        root.addView(txtRole);
        root.addView(btnLogout);

        btnLogout.setOnClickListener(v -> {
            requireActivity()
                    .getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::bindProfile);
        viewModel.loadProfile();
    }

    private void bindProfile(ProfileUser user) {
        if (user == null) {
            txtUsername.setText("Tai khoan: Chua dang nhap");
            txtFullName.setText("Ho ten: -");
            txtPhone.setText("So dien thoai: -");
            txtAddress.setText("Dia chi: -");
            txtRole.setText("Vai tro: -");
            return;
        }

        txtUsername.setText("Tai khoan: " + safe(user.getUsername()));
        txtFullName.setText("Ho ten: " + safe(user.getFullName()));
        txtPhone.setText("So dien thoai: " + safe(user.getPhone()));
        txtAddress.setText("Dia chi: " + safe(user.getAddress()));
        txtRole.setText("Vai tro: " + safe(user.getRole()));
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (value * density);
    }
}


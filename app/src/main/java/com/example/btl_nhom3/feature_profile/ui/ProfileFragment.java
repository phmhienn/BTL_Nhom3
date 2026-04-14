package com.example.btl_nhom3.feature_profile.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.btl_nhom3.R;
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
    private Button btnEditProfile;
    private Button btnChangePassword;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container, false);

		txtUsername = view.findViewById(R.id.txtUsername);
		txtFullName = view.findViewById(R.id.txtFullName);
		txtPhone = view.findViewById(R.id.txtPhone);
		txtAddress = view.findViewById(R.id.txtAddress);
		btnEditProfile = view.findViewById(R.id.btnEditProfile);
		btnChangePassword = view.findViewById(R.id.btnChangePassword);
		btnLogout = view.findViewById(R.id.btnLogout);

    btnEditProfile.setOnClickListener(v -> openChangeInfoFragment());
    btnChangePassword.setOnClickListener(v -> openChangePasswordFragment());
		btnLogout.setOnClickListener(v -> logout());

		return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::bindProfile);
        viewModel.getActionMessage().observe(getViewLifecycleOwner(), message -> {
            if (message == null || message.trim().isEmpty()) {
                return;
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });
        viewModel.loadProfile();
    }

    private void bindProfile(ProfileUser user) {
        if (user == null) {
      txtUsername.setText("-");
      txtFullName.setText("-");
      txtPhone.setText("-");
      txtAddress.setText("-");
      btnEditProfile.setEnabled(false);
      btnChangePassword.setEnabled(false);
            return;
        }

    txtUsername.setText(safe(user.getUsername()));
    txtFullName.setText(safe(user.getFullName()));
    txtPhone.setText(safe(user.getPhone()));
    txtAddress.setText(safe(user.getAddress()));
    btnEditProfile.setEnabled(true);
    btnChangePassword.setEnabled(true);
    }

  private void openChangeInfoFragment() {
		requireActivity().getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.frameMain, new ChangeInfoFragment())
				.addToBackStack(null)
				.commit();
  }

  private void openChangePasswordFragment() {
		requireActivity().getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.frameMain, new ChangePasswordFragment())
				.addToBackStack(null)
				.commit();
  }

  private void logout() {
      // 1. Xóa sạch SharedPreferences để không bị lưu tên User cũ
      // Lưu ý: Kiểm tra xem tên file "USER" có khớp với lúc bạn Login không nhé
      SharedPreferences pref = getActivity().getSharedPreferences("UserFile", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = pref.edit();
      editor.clear();
      editor.apply();

      // 2. Chuyển hướng về màn hình Đăng nhập (LoginActivity)
      Intent intent = new Intent(getActivity(), LoginActivity.class);

      // 3. QUAN TRỌNG: Xóa sạch các màn hình cũ trong bộ nhớ (Stack)
      // Việc này giúp khi sang User mới, các Fragment sẽ được khởi tạo mới hoàn toàn
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

      startActivity(intent);

      // Đóng Activity hiện tại chứa Fragment này
      if (getActivity() != null) {
          getActivity().finish();
      }
  }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

}


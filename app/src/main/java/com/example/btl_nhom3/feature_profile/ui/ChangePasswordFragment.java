package com.example.btl_nhom3.feature_profile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_profile.viewmodel.ProfileViewModel;

public class ChangePasswordFragment extends Fragment {

    private ProfileViewModel viewModel;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        edtCurrentPassword = view.findViewById(R.id.edtCurrentPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        Button btnSubmit = view.findViewById(R.id.btnSubmitChangePassword);
        Button btnCancel = view.findViewById(R.id.btnCancelChangePassword);

        btnSubmit.setOnClickListener(v -> viewModel.changePassword(
                edtCurrentPassword.getText().toString(),
                edtNewPassword.getText().toString(),
                edtConfirmPassword.getText().toString()
        ));

        btnCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getActionMessage().observe(getViewLifecycleOwner(), message -> {
            if (message == null || message.trim().isEmpty()) {
                return;
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            if ("Đổi mật khẩu thành công".equals(message)) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}


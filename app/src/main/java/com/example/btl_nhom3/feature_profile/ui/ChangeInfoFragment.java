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
import com.example.btl_nhom3.feature_profile.model.ProfileUser;
import com.example.btl_nhom3.feature_profile.viewmodel.ProfileViewModel;

public class ChangeInfoFragment extends Fragment {

    private ProfileViewModel viewModel;
    private EditText edtFullName;
    private EditText edtPhone;
    private EditText edtAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_info, container, false);

        edtFullName = view.findViewById(R.id.edtFullName);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);
        Button btnSubmit = view.findViewById(R.id.btnSubmitChangeInfo);
        Button btnCancel = view.findViewById(R.id.btnCancelChangeInfo);

        btnSubmit.setOnClickListener(v -> viewModel.updateProfile(
                edtFullName.getText().toString(),
                edtPhone.getText().toString(),
                edtAddress.getText().toString()
        ));

        btnCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

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
            if ("Cập nhật thông tin thành công".equals(message)) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        viewModel.loadProfile();
    }

    private void bindProfile(ProfileUser user) {
        if (user == null) {
            return;
        }

        edtFullName.setText(safe(user.getFullName()));
        edtPhone.setText(safe(user.getPhone()));
        edtAddress.setText(safe(user.getAddress()));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}


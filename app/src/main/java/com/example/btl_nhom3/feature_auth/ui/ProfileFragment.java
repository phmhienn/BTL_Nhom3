package com.example.btl_nhom3.feature_auth.ui;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.btl_nhom3.R;

public class ProfileFragment extends Fragment {

    private static final String PREF_USER = "USER";

    Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {

            // clear user (nếu có login)
            requireActivity().getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            // quay về màn chính hoặc login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}
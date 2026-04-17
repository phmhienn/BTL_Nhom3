package com.example.btl_nhom3.feature_auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_auth.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

	public static final String EXTRA_USERNAME = "extra_username";
	public static final String EXTRA_PASSWORD = "extra_password";

	private EditText edtUsername;
	private EditText edtPassword;
	private EditText edtConfirmPassword;
	private EditText edtFullName;
	private EditText edtPhone;
	private EditText edtAddress;
	private TextView txtError;

	private RegisterViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		edtUsername = findViewById(R.id.edtRegUsername);
		edtPassword = findViewById(R.id.edtRegPassword);
		edtConfirmPassword = findViewById(R.id.edtRegConfirmPassword);
		edtFullName = findViewById(R.id.edtRegFullName);
		edtPhone = findViewById(R.id.edtRegPhone);
		edtAddress = findViewById(R.id.edtRegAddress);
		txtError = findViewById(R.id.txtRegError);
		Button btnRegister = findViewById(R.id.btnRegister);
		TextView txtBackLogin = findViewById(R.id.txtBackLogin);

		viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
		observeViewModel();

		btnRegister.setOnClickListener(v -> {
			txtError.setText("");
			viewModel.register(
					edtUsername.getText().toString(),
					edtPassword.getText().toString(),
					edtConfirmPassword.getText().toString(),
					edtFullName.getText().toString(),
					edtPhone.getText().toString(),
					edtAddress.getText().toString()
			);
		});

		txtBackLogin.setOnClickListener(v -> finish());
	}

	private void observeViewModel() {

		viewModel.getRegisterMessage().observe(this, message -> {
			if (message == null || message.trim().isEmpty()) {
				txtError.setVisibility(View.GONE);
			} else {
				txtError.setText(message);
				txtError.setVisibility(View.VISIBLE);
			}
		});

		viewModel.getRegisterUser().observe(this, user -> {
			if (user == null) {
				return;
			}

			Intent data = new Intent();
			data.putExtra(EXTRA_USERNAME, user.getUsername());
			data.putExtra(EXTRA_PASSWORD, edtPassword.getText().toString());
			setResult(RESULT_OK, data);
			finish();
		});
	}
}

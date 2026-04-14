package com.example.btl_nhom3.feature_auth.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.btl_nhom3.feature_admin.ui.AdminDashboardActivity;

import com.example.btl_nhom3.MainActivity;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.core.database.Database;
import com.example.btl_nhom3.feature_auth.model.User;
import com.example.btl_nhom3.feature_auth.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

	private static final String PREF_USER = "USER";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_ROLE = "role";

	private EditText edtUsername;
	private EditText edtPassword;
	private TextView txtError;
	private ProgressBar progressLogin;
	private TextView txtRegisterNow;

	private AuthViewModel viewModel;
	private final ActivityResultLauncher<Intent> registerLauncher =
			registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
				if (result.getResultCode() != RESULT_OK || result.getData() == null) {
					return;
				}
				String username = result.getData().getStringExtra(RegisterActivity.EXTRA_USERNAME);
				String password = result.getData().getStringExtra(RegisterActivity.EXTRA_PASSWORD);
				if (username != null) {
					edtUsername.setText(username);
				}
				if (password != null) {
					edtPassword.setText(password);
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Database db = new Database(this);
		db.getWritableDatabase();
		edtUsername = findViewById(R.id.edtUsername);
		edtPassword = findViewById(R.id.edtPassword);
		txtError = findViewById(R.id.txtError);
		progressLogin = findViewById(R.id.progressLogin);
		txtRegisterNow = findViewById(R.id.txtRegisterNow);
		Button btnLogin = findViewById(R.id.btnLogin);

		viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
		observeViewModel();

		btnLogin.setOnClickListener(v -> {
			txtError.setText("");
			viewModel.login(
					edtUsername.getText().toString(),
					edtPassword.getText().toString()
			);
		});

		txtRegisterNow.setOnClickListener(v ->
				registerLauncher.launch(new Intent(this, RegisterActivity.class))
		);
	}

	private void observeViewModel() {
		viewModel.getLoading().observe(this, this::renderLoading);

		viewModel.getErrorMessage().observe(this, message -> {
			if (message == null || message.trim().isEmpty()) {
				txtError.setVisibility(View.GONE);
			} else {
				txtError.setText(message);
				txtError.setVisibility(View.VISIBLE);
			}
		});

		viewModel.getLoginUser().observe(this, user -> {
			if (user != null) {
				saveSession(user);
				goAfterLogin(user);
			}
		});

	}

	private void renderLoading(Boolean isLoading) {
		boolean loading = isLoading != null && isLoading;
		progressLogin.setVisibility(loading ? View.VISIBLE : View.GONE);
	}

	private void saveSession(User user) {
		getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
				.edit()
				.putInt(KEY_USER_ID, user.getId())
				.putString(KEY_USERNAME, user.getUsername())
				.putString(KEY_ROLE, user.getRole())
				.apply();
	}

	private void goAfterLogin(User user) {
		Intent intent;
		if (user.isAdmin()) {
			intent = new Intent(this, AdminDashboardActivity.class);
		} else {
			intent = new Intent(this, MainActivity.class);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

}

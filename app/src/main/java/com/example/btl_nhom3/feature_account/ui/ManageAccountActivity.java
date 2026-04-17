package com.example.btl_nhom3.feature_account.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_account.adapter.AccountUserAdapter;
import com.example.btl_nhom3.feature_account.model.AccountUser;
import com.example.btl_nhom3.feature_account.repository.AccountRepository;

public class ManageAccountActivity extends AppCompatActivity {

    private static final String PREF_USER = "USER";
    private static final String KEY_USER_ID = "user_id";

    private RecyclerView rvUser;
    private EditText edtSearchUser;

    private AccountRepository repository;
    private AccountUserAdapter adapter;
    private int currentAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        rvUser = findViewById(R.id.rvUser);
        edtSearchUser = findViewById(R.id.edtSearchUser);
        ImageView btnSearchUser = findViewById(R.id.btnSearchUser);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        repository = new AccountRepository(this);
        adapter = new AccountUserAdapter(new AccountUserAdapter.AccountActionListener() {
            @Override
            public void onChangeRole(AccountUser user) {
                showRoleDialog(user);
            }

            @Override
            public void onDelete(AccountUser user) {
                confirmDelete(user);
            }
        });

        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setAdapter(adapter);

        currentAdminId = getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
                .getInt(KEY_USER_ID, -1);

        btnSearchUser.setOnClickListener(v -> loadData(edtSearchUser.getText().toString().trim()));

        loadData("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(edtSearchUser.getText().toString().trim());
    }

    private void loadData(String keyword) {
        adapter.submitList(repository.searchUsers(keyword));
    }

    private void showRoleDialog(AccountUser user) {
        String[] uiRoles = new String[]{"User", "Admin"};
        String[] realRoles = new String[]{"user", "admin"};
        int checkedItem = user.isAdmin() ? 1 : 0;

        new AlertDialog.Builder(this)
                .setTitle("Đổi quyền tài khoản")
                .setSingleChoiceItems(uiRoles, checkedItem, (dialog, which) -> {
                    String selectedRole = realRoles[which];

                    if (user.getId() == currentAdminId && !"admin".equals(selectedRole)) {
                        Toast.makeText(this, "Không thể tự hạ quyền tài khoản đang đăng nhập", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (user.isAdmin() && !"admin".equals(selectedRole) && repository.countAdmins() <= 1) {
                        Toast.makeText(this, "Phải giữ lại ít nhất 1 admin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean ok = repository.updateRole(user.getId(), selectedRole);
                    if (ok) {
                        Toast.makeText(this, "Đã cập nhật quyền", Toast.LENGTH_SHORT).show();
                        loadData(edtSearchUser.getText().toString().trim());
                    } else {
                        Toast.makeText(this, "Cập nhật quyền thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(AccountUser user) {
        if (user.getId() == currentAdminId) {
            Toast.makeText(this, "Không thể xóa tài khoản đang đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.isAdmin() && repository.countAdmins() <= 1) {
            Toast.makeText(this, "Phải giữ lại ít nhất 1 admin", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xóa tài khoản")
                .setMessage("Bạn chắc chắn muốn xóa tài khoản " + user.getUsername() + "?")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean ok = repository.deleteUser(user.getId());
                    if (ok) {
                        Toast.makeText(this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                        loadData(edtSearchUser.getText().toString().trim());
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}


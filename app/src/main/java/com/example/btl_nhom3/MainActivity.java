package com.example.btl_nhom3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.btl_nhom3.feature_cart.ui.CartActivity;
import com.example.btl_nhom3.feature_menu.ui.HomeFragment;
import com.example.btl_nhom3.feature_menu.ui.MenuFragment;
import com.example.btl_nhom3.feature_profile.ui.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        // mặc định: trang chủ
        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_menu) {
                fragment = new MenuFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                fragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            }


            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) return false;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit();

        return true;
    }
}
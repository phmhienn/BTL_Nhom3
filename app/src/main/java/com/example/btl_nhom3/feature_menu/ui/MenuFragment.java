package com.example.btl_nhom3.feature_menu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom3.R;
import com.example.btl_nhom3.feature_menu.adapter.CategoryAdapter;
import com.example.btl_nhom3.feature_menu.adapter.FoodAdapter;
import com.example.btl_nhom3.feature_menu.model.Food;
import com.example.btl_nhom3.feature_menu.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    RecyclerView rvCategory, rvFood;
    EditText edtSearch;
    ImageView btnSearch;

    List<Food> fullList = new ArrayList<>();
    List<Food> filteredList = new ArrayList<>();

    int currentCategory = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        rvCategory = view.findViewById(R.id.rvCategory);
        rvFood = view.findViewById(R.id.rvFood);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        rvCategory.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        rvFood.setLayoutManager(new LinearLayoutManager(getContext()));

        // LẤY DATA TRỰC TIẾP
        MenuRepository repo = new MenuRepository(getContext());
        fullList = repo.getFoods();

        filteredList = new ArrayList<>(fullList);
        rvFood.setAdapter(new FoodAdapter(filteredList));

        // CATEGORY (fake hoặc dùng repo nếu có)
        rvCategory.setAdapter(new CategoryAdapter(repo.getCategories(), categoryId -> {
            currentCategory = categoryId;
            applyFilter();
        }));

        // CLICK ICON SEARCH
        btnSearch.setOnClickListener(v -> applyFilter());

        // ENTER SEARCH
        edtSearch.setOnEditorActionListener((TextView v1, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE) {

                applyFilter();
                return true;
            }
            return false;
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(); // Gọi hàm load lại dữ liệu mỗi khi Fragment hiển thị lại
    }

    private void loadData() {
        MenuRepository repo = new MenuRepository(getContext());
        fullList = repo.getFoods();
        applyFilter(); // Cập nhật lại list đang hiển thị
    }

    private void applyFilter() {

        String keyword = edtSearch.getText().toString().toLowerCase().trim();

        filteredList.clear();

        // Lấy danh sách Categories từ Repo
        MenuRepository repo = new MenuRepository(getContext());
        List<com.example.btl_nhom3.feature_menu.model.Category> categories = repo.getCategories();

        int allCategoryId = -1;
        if (!categories.isEmpty()) {
            allCategoryId = categories.get(0).getId();
        }

        // Nếu lúc mới mở (currentCategory == 0) hoặc chưa chọn gì, mặc định là show ALL
        boolean isAll = (currentCategory == 0 || currentCategory == allCategoryId);

        for (Food f : fullList) {
            boolean matchCategory = isAll || (f.getCategoryId() == currentCategory);
            boolean matchName = f.getName().toLowerCase().contains(keyword);

            if (matchCategory && matchName) {
                filteredList.add(f);
            }
        }

        rvFood.setAdapter(new FoodAdapter(filteredList));
    }
}
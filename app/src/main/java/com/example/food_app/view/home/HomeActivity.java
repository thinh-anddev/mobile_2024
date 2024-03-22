package com.example.food_app.view.home;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityHomeBinding;
import com.example.food_app.model.Category;
import com.example.food_app.view.history.HistoryActivity;
import com.example.food_app.view.home.adapter.CategoryAdapter;
import com.example.food_app.view.profile.ProfileActivity;
import com.example.food_app.view.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    private CategoryAdapter categoryAdapter;

    @Override
    protected ActivityHomeBinding setViewBinding() {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initRcvCategory();
    }

    @Override
    protected void viewListener() {
        binding.btnHistory.setOnClickListener(v -> {
           startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
        });

        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        binding.clSearch.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SearchActivity.class)));


//        String displayName = user.getDisplayName();
//        String email = user.getEmail();
//        String uid = user.getUid();
//        Uri photoUrl = user.getPhotoUrl();
//        binding.tvSeeMore.setText(displayName + email + uid + photoUrl);
    }

    private void initRcvCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Pizza", false));
        categoryList.add(new Category("Drinks", false));
        categoryList.add(new Category("Snacks", false));
        categoryList.add(new Category("Sauce", false));
        categoryList.add(new Category("Rice", false));
        categoryList.add(new Category("Chicken", false));
        categoryList.add(new Category("Potato", false));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(categoryList, this);
        binding.rcvCategory.setLayoutManager(linearLayoutManager);
        binding.rcvCategory.setAdapter(categoryAdapter);
    }
}

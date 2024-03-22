package com.example.food_app.view.search;

import android.view.LayoutInflater;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    @Override
    protected ActivitySearchBinding setViewBinding() {
        return ActivitySearchBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }
}

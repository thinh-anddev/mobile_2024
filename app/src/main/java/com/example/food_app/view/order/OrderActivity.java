package com.example.food_app.view.order;

import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderBinding;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {
    @Override
    protected ActivityOrderBinding setViewBinding() {
        return ActivityOrderBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}

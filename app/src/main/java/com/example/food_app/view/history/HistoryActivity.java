package com.example.food_app.view.history;

import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityHistoryBinding;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {
    @Override
    protected ActivityHistoryBinding setViewBinding() {
        return ActivityHistoryBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }
}

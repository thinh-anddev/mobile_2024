package com.example.food_app.view.profile;

import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityProfileBinding;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {
    @Override
    protected ActivityProfileBinding setViewBinding() {
        return ActivityProfileBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.btnCard.setOnClickListener(v -> {
            binding.imvTickCard.setActivated(true);
            binding.imvTickPaypal.setActivated(false);
        });

        binding.btnPaypal.setOnClickListener(v -> {
            binding.imvTickCard.setActivated(false);
            binding.imvTickPaypal.setActivated(true);
        });
    }
}

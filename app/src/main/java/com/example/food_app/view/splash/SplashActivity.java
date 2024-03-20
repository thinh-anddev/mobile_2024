package com.example.food_app.view.splash;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySplashBinding;
import com.example.food_app.view.user.UserActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        binding.btnGetStarted.setOnClickListener(view -> {
            startActivity(new Intent(SplashActivity.this, UserActivity.class));
            finish();
        });
    }

    @Override
    protected void viewListener() {

    }
}

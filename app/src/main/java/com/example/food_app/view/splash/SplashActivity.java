package com.example.food_app.view.splash;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySplashBinding;
import com.example.food_app.model.Food;
import com.example.food_app.repository.Repository;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.user.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    public List<Food> foodList;
    private boolean firstInstall = true;
    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void viewListener() {
        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first install app
                SharePreferenceUtils.putBoolean(Constant.FIRST_INSTALL,firstInstall);
                firstInstall = false;
                startActivity(new Intent(SplashActivity.this, UserActivity.class));
                finish();
            }
        });
    }
}

package com.example.food_app.view.splash;

import android.content.Intent;
import android.view.LayoutInflater;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySplashBinding;
import com.example.food_app.model.Food;
import com.example.food_app.view.user.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    public List<Food> foodList;
    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
//        foodList = new ArrayList<>();
//        if(SharePreferenceUtils.getBoolean(Constant.DATA_PRODUCT,true)) {
//            foodList.addAll(Repository.Companion.listFood());
//            rf.child("Foods").setValue(foodList);
//            SharePreferenceUtils.putBoolean(Constant.DATA_PRODUCT,false);
//        }
    }

    @Override
    protected void viewListener() {
        binding.btnGetStarted.setOnClickListener(view -> {
            startActivity(new Intent(SplashActivity.this, UserActivity.class));
            finish();
        });
    }
}

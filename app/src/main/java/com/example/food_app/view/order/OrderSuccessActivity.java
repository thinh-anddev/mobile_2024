package com.example.food_app.view.order;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderSuccessBinding;
import com.example.food_app.view.home.HomeActivity;

public class OrderSuccessActivity extends BaseActivity<ActivityOrderSuccessBinding> {
    @Override
    protected ActivityOrderSuccessBinding setViewBinding() {
        return ActivityOrderSuccessBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class));
            }
        });
    }

    @Override
    protected void viewListener() {

    }
}

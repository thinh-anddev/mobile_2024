package com.example.food_app.view.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityEditUserBinding;
import com.example.food_app.model.User;

public class EditUserActivity extends BaseActivity<ActivityEditUserBinding> {
    private User user;
    @Override
    protected ActivityEditUserBinding setViewBinding() {
        return ActivityEditUserBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("User");
            setUpProfile(user);
        }
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setUpProfile(User user) {
        binding.edtName.setHint(user.getName());
        binding.edtContact.setHint(user.getContact());
        binding.tvAddress.setText(user.getAddress());
    }
}

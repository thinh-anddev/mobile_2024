package com.example.food_app.view.splash;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySplashBinding;
import com.example.food_app.model.Food;
import com.example.food_app.repository.Repository;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.home.HomeActivity;
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
        readJsonToObjectFromUrl("https://raw.githubusercontent.com/thinh-nlu/cheatingFoodApp/main/cheating.json");
    }

    @Override
    protected void viewListener() {
        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first install app
                SharePreferenceUtils.putBoolean(Constant.FIRST_INSTALL,firstInstall);
                firstInstall = false;
                if (!SharePreferenceUtils.getString(Constant.USERNAME,"").equals("")
                        && !SharePreferenceUtils.getString(Constant.PASSWORD,"").equals("")) {
                    mAuth.signInWithEmailAndPassword(SharePreferenceUtils.getString(Constant.USERNAME,"")
                                    , SharePreferenceUtils.getString(Constant.PASSWORD,""))
                            .addOnCompleteListener( task -> {
                                if (task.isSuccessful()) {
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                    Toast.makeText(SplashActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SplashActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    startActivity(new Intent(SplashActivity.this, UserActivity.class));
                    finish();
                }
            }
        });
    }
}

package com.example.food_app.view.user;

import android.view.LayoutInflater;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityUserBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserActivity extends BaseActivity<ActivityUserBinding> {
    @Override
    protected ActivityUserBinding setViewBinding() {
        return ActivityUserBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initUserPager();
        binding.vpUser.setUserInputEnabled(false);
    }

    @Override
    protected void viewListener() {

    }

    private void initUserPager() {
        UserPagerAdapter adapter = new UserPagerAdapter(this);
        binding.vpUser.setAdapter(adapter);

        new TabLayoutMediator(binding.tabUser, binding.vpUser, (tab, position) -> {
            if (position == 0) {
                tab.setText("Đăng nhập");
                tab.view.setBackgroundResource(R.drawable.bg_user_mode_select);
            } else {
                tab.setText("Đăng ký");
                tab.view.setBackgroundResource(R.drawable.bg_user_mode_unselect);
            }
        }).attach();

        binding.tabUser.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.bg_user_mode_select);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.bg_user_mode_unselect);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

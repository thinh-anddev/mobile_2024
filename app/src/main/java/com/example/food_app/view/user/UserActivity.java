package com.example.food_app.view.user;

import android.app.ProgressDialog;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityUserBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends BaseActivity<ActivityUserBinding> {
    public User currentUser;
    ProgressDialog dialog;
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

    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    public void getUserFromFirebase(CallBack.OnDataLoad listener) {
        rf.child("Users").child(user != null ? user.getUid() : "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(User.class);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public User getCurrentUser() {
        return currentUser;
    }

}

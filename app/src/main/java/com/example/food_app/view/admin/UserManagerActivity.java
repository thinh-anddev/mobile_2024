package com.example.food_app.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityUserManagerBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.example.food_app.view.admin.adapter.UserManagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagerActivity extends BaseActivity<ActivityUserManagerBinding> {
    private List<User> userList = new ArrayList<>();
    private UserManagerAdapter adapter;
    @Override
    protected ActivityUserManagerBinding setViewBinding() {
        return ActivityUserManagerBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        getAllUserFromFB(() -> initAdapter());
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void getAllUserFromFB(CallBack.OnDataLoad listener) {
        userList.clear();
        rf.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initAdapter() {
        adapter = new UserManagerAdapter(this, userList, new UserManagerAdapter.OnClickOnButton() {
            @Override
            public void onEdit(String id) {
                findUserById(id);
            }
        });
        binding.rcvUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvUser.setAdapter(adapter);
    }

    private void findUserById(String id) {
        rf.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    User user = snapshot.getValue(User.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User",user);
                    Intent intent = new Intent(UserManagerActivity.this, EditUserActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

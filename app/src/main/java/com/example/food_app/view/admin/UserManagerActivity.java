package com.example.food_app.view.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityUserManagerBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.example.food_app.view.admin.adapter.UserManagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagerActivity extends BaseActivity<ActivityUserManagerBinding> {
    private List<User> userList = new ArrayList<>();
    private UserManagerAdapter adapter;
    ProgressDialog dialog;
    @Override
    protected ActivityUserManagerBinding setViewBinding() {
        return ActivityUserManagerBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getAllUserFromFB(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialog.cancel();
                initAdapter();
            }
        });
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

            @Override
            public void onBlock(String id) {
                blockUser(id);
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

    private void blockUser(String id) {
        rf.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    User user = snapshot.getValue(User.class);
                    user.setBlock(user.isBlock() ? false : true);
                    rf.child("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (user.isBlock()) {
                                    Toast.makeText(UserManagerActivity.this, "Đã chặn User này", Toast.LENGTH_SHORT).show();
                                } else Toast.makeText(UserManagerActivity.this, "Đã bỏ chặn User này", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

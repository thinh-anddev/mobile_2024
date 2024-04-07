package com.example.food_app.view.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityProfileBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {
    private List<User> userList = new ArrayList<>();
    private User currentUser = null;
    ProgressDialog progressDialog;
    private String address;
    @Override
    protected ActivityProfileBinding setViewBinding() {
        return ActivityProfileBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        address = SharePreferenceUtils.getString(Constant.ADDRESS,"");
        initLoadingData();
        getUserFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                progressDialog.cancel();
                setUpProfile();
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.tvChange.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, ChangeInfoActivity.class));
        });
    }

    private void getUserFromFirebase(CallBack.OnDataLoad listener) {
        userList.clear();
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

    private void updateUserToFirebase() {
        rf.child("User").child(user != null ? user.getUid() : "").setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void setUpProfile() {
        binding.tvName.setText(currentUser.getName());
        binding.tvEmail.setText(currentUser.getEmail());
        binding.tvContact.setText(currentUser.getContact());
        binding.tvAddress.setText(!currentUser.getAddress().equals("") ? currentUser.getAddress() : "Hãy cập nhật đỉa chỉ của bạn");
    }
    private void initLoadingData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang tai data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}

package com.example.food_app.view.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityChangeInfoBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ChangeInfoActivity extends BaseActivity<ActivityChangeInfoBinding> {
    private User currentUser =null ;
    private String address;
    ProgressDialog dialog;
    @Override
    protected ActivityChangeInfoBinding setViewBinding() {
        return ActivityChangeInfoBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            address = bundle.getString(Constant.ADDRESS);
        }
        getUserFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialog.cancel();
                currentUser.setAddress(address);
                binding.edtName.setText(currentUser.getName());
                binding.edtContact.setText(currentUser.getContact());
                binding.tvAddress.setText(currentUser.getAddress());
                listener();
            }
        });
    }

    @Override
    protected void viewListener() {
    }

    private void listener() {
        Log.d("ccc",currentUser.getName());
        binding.clAddress.setOnClickListener(v -> {
            startActivity(new Intent(ChangeInfoActivity.this, ChooseAddress.class));
        });
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnDone.setOnClickListener(v -> {
            currentUser.setName(binding.edtName.getText().equals("") ? currentUser.getName() : binding.edtName.getText()+"");
            currentUser.setContact(binding.edtContact.getText().equals("") ? currentUser.getContact() : binding.edtContact.getText()+"");
            updateUserToFirebase();
            Intent intent = new Intent(ChangeInfoActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void getUserFromFirebase(CallBack.OnDataLoad listener) {
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
        rf.child("Users").child(user != null ? user.getUid() : "").setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Dang tai data");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }
}

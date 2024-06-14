package com.example.food_app.view.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityProfileBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.splash.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {
    private User currentUser = null;
    ProgressDialog progressDialog;
    private String address;
    Order order;

    private List<Order> listOrderPending = new ArrayList<>();
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
            Intent intent = new Intent(ProfileActivity.this, ChangeInfoActivity.class);
            intent.putExtra("FROM","PROFILE");
            intent.putExtra("INVOICE_NUMBER","");
            startActivity(intent);
        });

        binding.btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.btnUpdateAvatar.setOnClickListener(v -> {
            checkPermissionCamera();
        });

        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
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
        Glide.with(this).load(SharePreferenceUtils.getString(Constant.AVATAR, "")).into(binding.imvProfile);
    }
    private void initLoadingData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang tai data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void checkPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 1222);
            } else {
                selectImageFromGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1222);
            } else {
                selectImageFromGallery();
            }
        }
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 100);
    }

    private void signOut() {
        mAuth.signOut();
        // Chuyển hướng người dùng đến màn hình đăng nhập hoặc màn hình chính
        Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        SharePreferenceUtils.putString(Constant.USERNAME,"");
        SharePreferenceUtils.putString(Constant.PASSWORD,"");
        startActivity(intent);
        finish(); // Đảm bảo người dùng không thể quay lại màn hình này bằng nút back
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(data.getData().toString()).into(binding.imvProfile);
                SharePreferenceUtils.putString(Constant.AVATAR, data.getData().toString());
                Log.d("imageActi", data.getData().toString()+"---");
            }
        }
    }
}

package com.example.food_app.view.admin;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityCreateUserBinding;
import com.example.food_app.model.User;
import com.example.food_app.view.user.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUserActivity extends BaseActivity<ActivityCreateUserBinding> {
    FirebaseAuth mAuth;
    private String email;
    private String password;
    private String contact;
    @Override
    protected ActivityCreateUserBinding setViewBinding() {
        return ActivityCreateUserBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void viewListener() {
        binding.btnRegister.setOnClickListener(v -> {
            email = String.valueOf(binding.edtUsername.getText());
            password = String.valueOf(binding.edtPassword.getText());
            contact = String.valueOf(binding.edtContact.getText());
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                Toast.makeText(CreateUserActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 8) {
                Toast.makeText(CreateUserActivity.this, "Vui lòng nhập mật khẩu tối thiểu 8 chữ số", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), email,getNameFromEmail(email),"","",contact,"0",false);
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task2 -> {
                                        Toast.makeText(CreateUserActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CreateUserActivity.this, UserActivity.class));
                                    });
                                });
                            } else {
                                Log.d("hhhh","Registration failed: " + task.getException().getMessage());
                                Toast.makeText(CreateUserActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    private String getNameFromEmail(String email) {
        String[] parts = email.split("@");
        String name = parts[0];
        return name;
    }
}

package com.example.food_app.view.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityEditUserBinding;
import com.example.food_app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserActivity extends BaseActivity<ActivityEditUserBinding> {
    private User user;
    private DatabaseReference userRef;

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


        userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getId());
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnDone.setOnClickListener(v -> {
            saveProfileChanges();
        });
    }

    private void setUpProfile(User user) {
        binding.edtName.setText(user.getName());
        binding.edtContact.setText(user.getContact());
        binding.edtAddress.setText(user.getAddress());

    }

    private void saveProfileChanges() {
        String name = binding.edtName.getText().toString().trim();
        String contact = binding.edtContact.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setName(name);
        user.setContact(contact);
        user.setAddress(address);

        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditUserActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditUserActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

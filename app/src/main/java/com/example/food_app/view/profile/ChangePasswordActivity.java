package com.example.food_app.view.profile;

import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding> {
    private String oldPassword = "";
    private String newPassword = "";
    private String confirmPass = "";
    @Override
    protected ActivityChangePasswordBinding setViewBinding() {
        return ActivityChangePasswordBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void viewListener() {
        binding.btnDone.setOnClickListener(v -> {
            newPassword = String.valueOf(binding.edtNewPass.getText());
            oldPassword = String.valueOf(binding.edtOldPass.getText());
            confirmPass = String.valueOf(binding.edtConfirmPass.getText());
            if (newPassword.equals(confirmPass)) {
                changPassword(oldPassword, newPassword);
            } else {
                Toast.makeText(ChangePasswordActivity.this,"Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changPassword(String oldPassword, String newPassword) {
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // mật khẩu cũ đúng
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        Toast.makeText(ChangePasswordActivity.this, "Weak password.", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(ChangePasswordActivity.this, "Password update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }                                }
                            }
                        });
                    } else {
                        // mật khẩu cũ sai
                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}

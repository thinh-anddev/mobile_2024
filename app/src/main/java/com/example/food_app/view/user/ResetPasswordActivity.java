package com.example.food_app.view.user;

import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> {
    @Override
    protected ActivityResetPasswordBinding setViewBinding() {
        return ActivityResetPasswordBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnConfirm.setOnClickListener(v -> {
            String email = String.valueOf(binding.edtEmail.getText());
            resetPassword(email);
        });
    }

    private void resetPassword(String email) {
        if (email.equals("")) {
            Toast.makeText(this, "Vui lòng nhập email của bạn", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Mật khẩu mới đã được gửi đến Email của bạn", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(ResetPasswordActivity.this, "No account found with this email", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthException e) {
                            Toast.makeText(ResetPasswordActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(ResetPasswordActivity.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}

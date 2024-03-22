package com.example.food_app.view.user;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.food_app.base.BaseFragment;
import com.example.food_app.databinding.FragmentRegisterBinding;
import com.example.food_app.view.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    FirebaseAuth mAuth;
    private String email;
    private String password;
    private String cPassword;
    @Override
    protected FragmentRegisterBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentRegisterBinding.inflate(inflater, viewGroup, false);
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
            cPassword = String.valueOf(binding.edtCPassword.getText());
            if (email == null || email.isEmpty() || password == null || password.isEmpty() || cPassword == null || cPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(cPassword)) {
                Toast.makeText(requireContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            } else if (password.length() <= 8) {
                Toast.makeText(requireContext(), "Vui lòng nhập mật khẩu tối thiểu 8 chữ số", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(requireContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(requireActivity(), UserActivity.class));
                            } else {
                                Log.d("hhhh","Registration failed: " + task.getException().getMessage());
                            }
                        });
            }
        });
    }
}

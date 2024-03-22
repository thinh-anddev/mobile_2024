package com.example.food_app.view.user;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.food_app.base.BaseFragment;
import com.example.food_app.databinding.FragmentLoginBinding;
import com.example.food_app.view.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    FirebaseAuth mAuth;
    private String email;
    private String password;
    @Override
    protected FragmentLoginBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentLoginBinding.inflate(inflater, viewGroup, false);
    }

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void viewListener() {
        binding.btnLogin.setOnClickListener(v -> {
            email = String.valueOf(binding.edtUsername.getText());
            password = String.valueOf(binding.edtPassword.getText());
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( task -> {
                            if (task.isSuccessful()) {
                                //FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(requireActivity(), HomeActivity.class));
                                Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}

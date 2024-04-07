package com.example.food_app.view.user;

import android.app.ProgressDialog;
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
import com.example.food_app.model.User;
import com.example.food_app.view.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    FirebaseAuth mAuth;
    private String email;
    private String password;
    private String cPassword;
    private String contact;
    private ProgressDialog dialog;
    @Override
    protected FragmentRegisterBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentRegisterBinding.inflate(inflater, viewGroup, false);
    }

    @Override
    protected void initView() {
        mAuth = FirebaseAuth.getInstance();
        initLoadingData();
    }

    @Override
    protected void viewListener() {
        binding.btnRegister.setOnClickListener(v -> {
            dialog.show();
            email = String.valueOf(binding.edtUsername.getText());
            password = String.valueOf(binding.edtPassword.getText());
            cPassword = String.valueOf(binding.edtCPassword.getText());
            contact = String.valueOf(binding.edtContact.getText());
            if (email == null || email.isEmpty() || password == null || password.isEmpty() || cPassword == null || cPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else if (!password.equals(cPassword)) {
                Toast.makeText(requireContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else if (password.length() < 8) {
                Toast.makeText(requireContext(), "Vui lòng nhập mật khẩu tối thiểu 8 chữ số", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                User user = new User(email,getNameFromEmail(email),"","",contact,"0");
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task2 -> {
                                        Toast.makeText(requireContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        startActivity(new Intent(requireActivity(), UserActivity.class));
                                    });
                                });
                            } else {
                                Log.d("hhhh","Registration failed: " + task.getException().getMessage());
                                dialog.cancel();
                                Toast.makeText(requireActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    private String getNameFromEmail(String email) {
        String[] parts = email.split("@");
        String name = parts[0];
        return name;
    }
}

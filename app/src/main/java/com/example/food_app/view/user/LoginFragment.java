package com.example.food_app.view.user;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.food_app.base.BaseFragment;
import com.example.food_app.databinding.FragmentLoginBinding;
import com.example.food_app.view.home.HomeActivity;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    @Override
    protected FragmentLoginBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentLoginBinding.inflate(inflater, viewGroup, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {
        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), HomeActivity.class));
            requireActivity().finish();
        });
    }
}

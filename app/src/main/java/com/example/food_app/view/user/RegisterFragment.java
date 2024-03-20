package com.example.food_app.view.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.food_app.base.BaseFragment;
import com.example.food_app.databinding.FragmentRegisterBinding;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    @Override
    protected FragmentRegisterBinding setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup) {
        return FragmentRegisterBinding.inflate(inflater, viewGroup, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void viewListener() {

    }
}

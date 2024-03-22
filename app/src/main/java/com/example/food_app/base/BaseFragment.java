package com.example.food_app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;

    protected abstract T setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup);

    //setupView here
    protected abstract void initView();

    //listen to user action here
    protected abstract void viewListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        viewListener();
    }
}

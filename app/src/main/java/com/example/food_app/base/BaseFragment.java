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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;
    protected FirebaseAuth mAuth;
    protected FirebaseUser user;
    protected FirebaseDatabase db;
    protected DatabaseReference rf;

    protected abstract T setViewBinding(LayoutInflater inflater, @Nullable ViewGroup viewGroup);

    //setupView here
    protected abstract void initView();

    //listen to user action here
    protected abstract void viewListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        rf = db.getReference();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        viewListener();
    }
}

package com.example.food_app.view.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.view.home.adapter.OrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {
    private User currentUser = null;
    private Order order;
    private OrderAdapter orderAdapter;
    ProgressDialog progressDialog;
    @Override
    protected ActivityOrderBinding setViewBinding() {
        return ActivityOrderBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getUserFromFirebase(() -> {
            setUpProfile();
            progressDialog.cancel();
        });

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        initAdapter();
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });


    }

    private void initAdapter() {
        orderAdapter = new OrderAdapter(this, order);
        binding.rvFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvFood.setAdapter(orderAdapter);
    }

    private void getUserFromFirebase(CallBack.OnDataLoad listener) {
        rf.child("Users").child(user != null ? user.getUid() : "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(User.class);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setUpProfile() {
        binding.tvName.setText(currentUser.getName());
        binding.tvEmail.setText(currentUser.getEmail());
        binding.tvContact.setText(currentUser.getContact());
        binding.tvAddress.setText(!currentUser.getAddress().equals("") ? currentUser.getAddress() : "Hãy cập nhật đỉa chỉ của bạn");
    }


    private void initLoadingData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang tai data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}

package com.example.food_app.view.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.home.adapter.OrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {
    private User currentUser = null;
    private Order order;
    private String actionOrder;
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
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            order = (Order) bundle.getSerializable("order");
            actionOrder = bundle.getString("actionOrder");
        }

        initViewOrder();

        initAdapter();

        binding.tvSumValue.setText(String.valueOf(countSumPrice(order)));
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
    private void initViewOrder() {
        switch (actionOrder) {
            case Constant.NOT_CHECK_OUT:
                binding.btnStartOrder.setText("Thanh toán");
                break;
            case Constant.CHECK_OUT:
                binding.btnStartOrder.setText("Đặt lại");
                break;
//            default:
//                binding.btnStartOrder.setText("Thanh toán");
        }
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

    private double countSumPrice(Order order) {
        double sum = 0.0;
        for (Cart c: order.getFoodList()) {
            sum += Double.valueOf(c.getNumber()) * c.getFood().getPrice();
        }
        return sum;
    }
    private void initLoadingData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang tai data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}

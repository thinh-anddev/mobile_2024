package com.example.food_app.view.admin;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderDispatchBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.admin.adapter.OrderDispatchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDispatchActivity extends BaseActivity<ActivityOrderDispatchBinding> {
    private OrderDispatchAdapter adapter;
    private List<Order> orderList = new ArrayList<>();
    @Override
    protected ActivityOrderDispatchBinding setViewBinding() {
        return ActivityOrderDispatchBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        getListOrderFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                initAdapter();
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void initAdapter() {
        adapter = new OrderDispatchAdapter(this, orderList);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcv.setAdapter(adapter);
    }

    private void getListOrderFromFirebase(CallBack.OnDataLoad listener) {
        orderList.clear();
        rf.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Assuming each child in "Order" node is a list of orders per user
                    for (DataSnapshot userOrderSnapshot : dataSnapshot.getChildren()) {
                        Order order = userOrderSnapshot.getValue(Order.class);
                        if (order.getStatus().equals(Constant.PENDING)) {
                            orderList.add(order);
                        }
                    }
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}

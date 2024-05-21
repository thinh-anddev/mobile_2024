package com.example.food_app.view.history;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityHistoryBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.example.food_app.model.Order;
import com.example.food_app.view.home.adapter.HistoryOrderAdapter;
import com.example.food_app.view.order.OrderActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {
    private List<Order> orderList = new ArrayList<>();
    private HistoryOrderAdapter adapter;
    private Order order;
    private ProgressDialog dialogLoading;
    @Override
    protected ActivityHistoryBinding setViewBinding() {
        return ActivityHistoryBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getListOrderFromFirebase(() -> {
            dialogLoading.cancel();
            Log.d("orderList", orderList.size()+"");
            setUpEmptyOrderScreen();
            initAdapter();
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void getListOrderFromFirebase(CallBack.OnDataLoad listener) {
        orderList.clear();
        rf.child("Order").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    order = dataSnapshot.getValue(Order.class);
                    orderList.add(order);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initAdapter() {
        adapter = new HistoryOrderAdapter(this, orderList, new HistoryOrderAdapter.onClickOrderListener() {
            @Override
            public void onClick(Order order) {
                Intent intent = new Intent(HistoryActivity.this, OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("actionOrder",order.getStatus());
                bundle.putSerializable("order",order);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvOrder.setLayoutManager(layoutManager);
        binding.rvOrder.setAdapter(adapter);
    }
    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }
    private void setUpEmptyOrderScreen() {
        if (orderList.isEmpty()) {
            binding.clEmptyOrder.setVisibility(View.VISIBLE);
            binding.rvOrder.setVisibility(View.GONE);
        } else {
            binding.clEmptyOrder.setVisibility(View.GONE);
            binding.rvOrder.setVisibility(View.VISIBLE);
        }
    }
}

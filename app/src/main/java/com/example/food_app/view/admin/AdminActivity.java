package com.example.food_app.view.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityAdminBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.home.seemore.SeeMoreActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends BaseActivity<ActivityAdminBinding> {
    private List<Order> orderList = new ArrayList<>();
    ProgressDialog dialogLoading;

    @Override
    protected ActivityAdminBinding setViewBinding() {
        return ActivityAdminBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
    }

    @Override
    protected void viewListener() {
        binding.btnAllFood.setOnClickListener(v -> {
            startActivity(new Intent(this, SeeMoreActivity.class));
        });

        binding.btnOrderDispatch.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderDispatchActivity.class));
        });
    }

    private int filterOrderPending() {
        List<Order> orders = new ArrayList<>();
        for (Order o: orderList) {
            if (o.getStatus().equals(Constant.PENDING)) {
                orders.add(o);
            }
        }
        return orders.size();
    }

    private int filterCompleteOrder() {
        List<Order> orders = new ArrayList<>();
        for (Order o: orderList) {
            if (o.getStatus().equals(Constant.DELIVERED)) {
                orders.add(o);
            }
        }
        return orders.size();
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
                        orderList.add(order);
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

    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListOrderFromFirebase(() -> {
            binding.tvCountPendingOrder.setText(String.valueOf(filterOrderPending()));
            binding.tvCountCompleteOrder.setText(String.valueOf(filterCompleteOrder()));
            dialogLoading.cancel();
        });
    }
}

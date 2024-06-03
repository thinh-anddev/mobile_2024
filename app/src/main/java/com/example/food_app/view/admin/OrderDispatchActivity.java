package com.example.food_app.view.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderDispatchBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.admin.adapter.OrderDispatchAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        adapter = new OrderDispatchAdapter(this, orderList, new OrderDispatchAdapter.OnClickOrder() {
            @Override
            public void onClick(String invoiceNumber) {
                for (Order o: orderList) {
                    if (o.getInvoiceNumber().equals(invoiceNumber)) {
                        o.setStatus(Constant.DELIVERED);
                        updateOrder(o, invoiceNumber);
                    }
                }
                adapter.updateOrderList(orderList);
            }
        });
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

    public void updateOrder(Order order, String invoiceNumber) {
        Log.d("getInvoiceNumber", order.getInvoiceNumber());
        rf.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()){
                    String userUID = user.getKey();
                    if (user.hasChild(invoiceNumber)){
                        rf.child("Order").child(userUID).child(invoiceNumber).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(OrderDispatchActivity.this, "Đơn hàng đã được lên", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("updateOrder", "Failed", task.getException());
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

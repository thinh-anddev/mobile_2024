package com.example.food_app.view.profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityChangeInfoBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.order.OrderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ChangeInfoActivity extends BaseActivity<ActivityChangeInfoBinding> {
    private User currentUser = null ;
    private Order currentOrder = null;
    private String address = "";
    private String actionFrom = "";
    private String invoiceNumber = "";
    ProgressDialog dialog;
    @Override
    protected ActivityChangeInfoBinding setViewBinding() {
        return ActivityChangeInfoBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
    }

    @Override
    protected void viewListener() {
    }
    private void pushViewFromAction() {
        if (actionFrom.equals("PROFILE")) {
            if (!address.equals("")) {
                binding.tvAddress.setText(address);
            } else {
                binding.tvAddress.setText(currentUser.getAddress());
            }
            binding.edtName.setText(currentUser.getName());
            binding.edtContact.setText(currentUser.getContact());
        } else {
            if (!address.equals("")) {
                binding.tvAddress.setText(address);
            } else {
                binding.tvAddress.setText(currentOrder.getAddress());
            }
            binding.edtName.setText(currentOrder.getNameUser());
            binding.edtContact.setText(currentOrder.getContact());
        }
    }

    private void listener() {
        Log.d("ccc",currentUser.getName());
        binding.clAddress.setOnClickListener(v -> {
            startActivity(new Intent(ChangeInfoActivity.this, ChooseAddress.class));
        });
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnDone.setOnClickListener(v -> {
            if (actionFrom.equals("PROFILE")) {
                currentUser.setName(binding.edtName.getText().equals("") ? currentUser.getName() : binding.edtName.getText()+"");
                currentUser.setContact(binding.edtContact.getText().equals("") ? currentUser.getContact() : binding.edtContact.getText()+"");
                currentUser.setAddress(binding.tvAddress.getText()+"");
                updateUserToFirebase();
                Intent intent = new Intent(ChangeInfoActivity.this, ProfileActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                currentOrder.setNameUser(binding.edtName.getText().equals("") ? currentOrder.getNameUser() : binding.edtName.getText()+"");
                currentOrder.setContact(binding.edtContact.getText().equals("") ? currentOrder.getContact() : binding.edtContact.getText()+"");
                currentOrder.setAddress(binding.tvAddress.getText()+"");
                updateOrderToFirebase();
                Intent intent = new Intent(ChangeInfoActivity.this, OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("actionOrder", currentOrder.getStatus());
                bundle.putSerializable("order",currentOrder);
                intent.putExtras(bundle);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
    private void getOrderByInvoiceNumber() {
        if (invoiceNumber != "") {
            rf.child("Order").child(user != null ? user.getUid() : "").child(invoiceNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        currentOrder = snapshot.getValue(Order.class);
                    }
                    pushViewFromAction();
                    listener();
                    dialog.cancel();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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

    private void updateUserToFirebase() {
        rf.child("Users").child(user != null ? user.getUid() : "").setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    private void updateOrderToFirebase() {
        rf.child("Order").child(user.getUid()).child(currentOrder.getInvoiceNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    rf.child("Order").child(user.getUid()).child(currentOrder.getInvoiceNumber()).setValue(currentOrder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Dang tai data");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        actionFrom = intent.getStringExtra("FROM");
        invoiceNumber = intent.getStringExtra("INVOICE_NUMBER");
        address = SharePreferenceUtils.getString(Constant.ADDRESS, "");
        Log.d("address", SharePreferenceUtils.getString(Constant.ADDRESS, ""));


        getUserFromFirebase(() -> {
            Log.d("ccc",currentUser.getName());
            getOrderByInvoiceNumber();
        });
    }
}

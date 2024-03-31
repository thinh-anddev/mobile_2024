package com.example.food_app.view.cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityCartBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.example.food_app.view.cart.adapter.CartAdapter;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.home.adapter.FoodAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity<ActivityCartBinding> {
    private List<Cart> cartList = new ArrayList<>();
    private ProgressDialog dialogLoading;
    private CartAdapter cartAdapter;
    @Override
    protected ActivityCartBinding setViewBinding() {
        return ActivityCartBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getListCartFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialogLoading.cancel();
                initCartAdapter();
                setUpCartScreen();
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            updateCartInFirebase();
            finish();
        });
    }

//    private void getListFood(CallBack.OnDataLoad listener) {
//        cartList.clear();
//        rf.child("Foods").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot d: snapshot.getChildren()) {
//                    Food food = d.getValue(Food.class);
//                    cartList.add(food);
//                }
//                listener.onDataLoad();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }

    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    private void initCartAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartAdapter = new CartAdapter(this, cartList, idFood -> {
//            Intent intent = new Intent(CartActivity.this, FoodDetailActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt("idFood",idFood);
//            intent.putExtras(bundle);
//            startActivity(intent);
        });
        binding.rcvCart.setLayoutManager(linearLayoutManager);
        binding.rcvCart.setAdapter(cartAdapter);
    }

    private void getListCartFromFirebase(CallBack.OnDataLoad listener) {
        cartList.clear();
        rf.child("Cart").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Food f = dataSnapshot.child("food").getValue(Food.class);
                    int number = dataSnapshot.child("number").getValue(Integer.class);
                    cartList.add(new Cart(f,number));
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateCartInFirebase() {
        rf.child("Cart").child(user != null ? user.getUid() : "").setValue(cartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    private void setUpCartScreen() {
        if (cartList.size() == 0) {
            binding.llContent.setVisibility(View.GONE);
            binding.rcvCart.setVisibility(View.GONE);
        } else {
            binding.llContent.setVisibility(View.VISIBLE);
            binding.rcvCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

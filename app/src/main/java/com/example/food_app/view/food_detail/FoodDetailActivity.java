package com.example.food_app.view.food_detail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.food_app.helper.CallBack;
import androidx.annotation.NonNull;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityFoodDetailBinding;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodDetailActivity extends BaseActivity<ActivityFoodDetailBinding> {
    private int idFood;
    private List<Food> foodList = new ArrayList<>();
    private Food food = null;
    ProgressDialog dialogLoading;
    private List<Cart> cartList = new ArrayList<>();
    @Override
    protected ActivityFoodDetailBinding setViewBinding() {
        return ActivityFoodDetailBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getListCartFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialogLoading.cancel();
            }
        });
        getListFood(() -> {
            dialogLoading.cancel();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                idFood = bundle.getInt("idFood");
                for (Food f: foodList) {
                    if (f.getId() == idFood) {
                        food = f;
                        binding.imvFood.setImageResource(f.getPhoto());
                        binding.tvNameFood.setText(f.getTitle());
                        binding.tvPrice.setText(String.valueOf(f.getPrice()));
                        binding.tvContent.setText(f.getDescription());
                    }
                }
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnAddToCart.setOnClickListener(v -> {
            cartList.add(new Cart(food,1));
            rf.child("Cart").child(user != null ? user.getUid() : "").setValue(cartList).addOnCompleteListener(task -> {
               Toast.makeText(this, "Da them vao gio hang",Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void getListFood(CallBack.OnDataLoad listener) {
        foodList.clear();
        rf.child("Foods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()) {
                    Food food = d.getValue(Food.class);
                    foodList.add(food);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getListCartFromFirebase(CallBack.OnDataLoad listener) {
        cartList.clear();
        rf.child("Cart").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    food = d.child("food").getValue(Food.class);
                    int number = d.child("number").getValue(Integer.class);
                    cartList.add(new Cart(food, number));
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

}

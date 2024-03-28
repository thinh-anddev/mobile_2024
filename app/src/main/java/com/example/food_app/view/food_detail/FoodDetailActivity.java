package com.example.food_app.view.food_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.example.food_app.helper.CallBack;
import androidx.annotation.NonNull;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityFoodDetailBinding;
import com.example.food_app.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends BaseActivity<ActivityFoodDetailBinding> {
    private int idFood;
    private List<Food> foodList = new ArrayList<>();
    @Override
    protected ActivityFoodDetailBinding setViewBinding() {
        return ActivityFoodDetailBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        getListFood(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    idFood = bundle.getInt("idFood");
                    for (Food f: foodList) {
                        if (f.getId() == idFood) {
                            binding.imvFood.setImageResource(f.getPhoto());
                            binding.tvNameFood.setText(f.getTitle());
                            binding.tvPrice.setText(String.valueOf(f.getPrice()));
                            binding.tvContent.setText(f.getDescription());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());
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
                Log.d("hehe",foodList.size()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}

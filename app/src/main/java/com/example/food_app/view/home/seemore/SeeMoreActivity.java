package com.example.food_app.view.home.seemore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySeeMoreBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Food;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.home.adapter.FoodAdapter;
import com.example.food_app.view.home.adapter.SeeMoreAdapter;
import com.example.food_app.view.search.SearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeMoreActivity extends BaseActivity<ActivitySeeMoreBinding> {
    private SeeMoreAdapter seeMoreAdapter;
    private List<Food> foodList = new ArrayList<>();
    private ProgressDialog loadingDataDialog;

    @Override
    protected ActivitySeeMoreBinding setViewBinding() {
        return ActivitySeeMoreBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getListFood(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                loadingDataDialog.cancel();
                initFoodAdapter();
            }
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void initFoodAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        seeMoreAdapter = new SeeMoreAdapter(this, foodList, idFood -> {
            Intent intent = new Intent(SeeMoreActivity.this, FoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFood",idFood);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.rcvAllFood.setLayoutManager(linearLayoutManager);
        binding.rcvAllFood.setAdapter(seeMoreAdapter);
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

    private void initLoadingData() {
        loadingDataDialog = new ProgressDialog(this);
        loadingDataDialog.setMessage("Dang tai data");
        loadingDataDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDataDialog.setCancelable(false);
        loadingDataDialog.show();
    }
}

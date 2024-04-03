package com.example.food_app.view.favourite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityCartBinding;
import com.example.food_app.databinding.ActivityFavouriteBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Food;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.adapter.SeeMoreAdapter;
import com.example.food_app.view.home.seemore.SeeMoreActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends BaseActivity<ActivityFavouriteBinding> {
    private List<Food> favouriteList = new ArrayList<>();
    private FavouriteAdapter favouriteAdapter;
    ProgressDialog dialogLoading;
    @Override
    protected ActivityFavouriteBinding setViewBinding() {
        return ActivityFavouriteBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        initLoadingData();
        getListFavouriteFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialogLoading.cancel();
                initAdapter();
                setUpScreen();
            }
        });

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void getListFavouriteFromFirebase(CallBack.OnDataLoad listener) {
        favouriteList.clear();
        rf.child("Favourite").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Food f = d.getValue(Food.class);
                    favouriteList.add(f);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favouriteAdapter = new FavouriteAdapter(this, favouriteList, idFood -> {
            Intent intent = new Intent(FavouriteActivity.this, FoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFood",idFood);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.rcvFar.setLayoutManager(linearLayoutManager);
        binding.rcvFar.setAdapter(favouriteAdapter);
    }

    private void setUpScreen() {
        if (favouriteList.isEmpty()) {
            binding.clContent.setVisibility(View.GONE);
            binding.tvContent.setVisibility(View.VISIBLE);
        } else {
            binding.clContent.setVisibility(View.VISIBLE);
            binding.tvContent.setVisibility(View.GONE);
        }
    }
}

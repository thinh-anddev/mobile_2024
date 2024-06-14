package com.example.food_app.view.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivitySearchBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Food;
import com.example.food_app.utils.CommonUtils;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.home.adapter.FoodAdapter;
import com.example.food_app.view.search.adapter.SearchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    private List<Food> foodList = new ArrayList<>();
    private List<Food> searchList = new ArrayList<>();
    private SearchAdapter foodAdapter;
    private String keyword;
    ProgressDialog dialog;
    @Override
    protected ActivitySearchBinding setViewBinding() {
        return ActivitySearchBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        binding.layoutNoFood.setVisibility(View.GONE);
        binding.tvCountFood.setVisibility(View.GONE);
        binding.rcvSearch.setVisibility(View.GONE);
        initLoadingData();
        getListFood(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialog.cancel();
            }
        });

    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSearch.setOnClickListener(v -> {
            dialog.show();
            keyword = String.valueOf(binding.edtSearch.getText());
            if (!keyword.isEmpty()) {
                searchList = getSearchListByKeyword(searchList, keyword, new CallBack.OnSearchData() {
                    @Override
                    public void onSearch() {
                        if (searchList.size() != 0) {
                            binding.tvCountFood.setVisibility(View.VISIBLE);
                            binding.tvCountFood.setText("Đã tìm thấy "+ searchList.size()+" sản phẩm cho bạn");
                            binding.rcvSearch.setVisibility(View.VISIBLE);
                            binding.layoutNoFood.setVisibility(View.GONE);
                            CommonUtils.hideSoftKeyboard(SearchActivity.this);
                        } else {
                            binding.layoutNoFood.setVisibility(View.VISIBLE);
                            binding.tvCountFood.setVisibility(View.GONE);
                            binding.rcvSearch.setVisibility(View.GONE);
                            CommonUtils.hideSoftKeyboard(SearchActivity.this);
                        }
                        dialog.cancel();
                    }
                });
                initFoodAdapter();
            } else {
                Toast.makeText(SearchActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            }
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

    private void initFoodAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        foodAdapter = new SearchAdapter(this, searchList, idFood -> {
            Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFood",idFood);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.rcvSearch.setLayoutManager(gridLayoutManager);
        binding.rcvSearch.setAdapter(foodAdapter);
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải Data");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    private List<Food> getSearchListByKeyword(List<Food> searchList,String keyword, CallBack.OnSearchData listener) {
        searchList.clear();
        for (Food f: foodList) {
            if (f.getTitle().toLowerCase().contains(keyword.trim())) {
                searchList.add(f);
            }
        }
        listener.onSearch();
        return searchList;
    }
}

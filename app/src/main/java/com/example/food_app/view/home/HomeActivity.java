package com.example.food_app.view.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityHomeBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Category;
import com.example.food_app.model.Food;
import com.example.food_app.repository.Repository;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.example.food_app.view.cart.CartActivity;
import com.example.food_app.view.favourite.FavouriteActivity;
import com.example.food_app.view.food_detail.FoodDetailActivity;
import com.example.food_app.view.history.HistoryActivity;
import com.example.food_app.view.home.adapter.CategoryAdapter;
import com.example.food_app.view.home.adapter.FoodAdapter;
import com.example.food_app.view.home.seemore.SeeMoreActivity;
import com.example.food_app.view.profile.ProfileActivity;
import com.example.food_app.view.search.SearchActivity;
import com.example.food_app.view.user.UserActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private List<Food> foodList = new ArrayList<>();
    private List<Food> filterList = new ArrayList<>();
    private String cate;
    private ProgressDialog loadingDataDialog;

    @Override
    protected ActivityHomeBinding setViewBinding() {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        if(SharePreferenceUtils.getBoolean(Constant.FIRST_INSTALL,false)) {
            foodList.addAll(Repository.listFood());
            rf.child("Foods").setValue(foodList);
            SharePreferenceUtils.putBoolean(Constant.FIRST_INSTALL,false);
        }

        initLoadingData();
        getListFood(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                loadingDataDialog.cancel();
                initRcvCategory();
                initFoodAdapter();
            }
        });
        Log.d("cqq",foodList.size()+"");
    }



    @Override
    protected void viewListener() {
        binding.btnHistory.setOnClickListener(v -> {
           startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
        });

        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        binding.tvSeeMore.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, SeeMoreActivity.class));
        });

        binding.btnCart.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        binding.btnFarvourite.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FavouriteActivity.class));
        });

        binding.clSearch.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SearchActivity.class)));
        binding.tvXoa.setOnClickListener(v -> {
            user.delete();
            startActivity(new Intent(HomeActivity.this, UserActivity.class));
        });

//        String displayName = user.getDisplayName();
//        String email = user.getEmail();
//        String uid = user.getUid();
//        Uri photoUrl = user.getPhotoUrl();
//        binding.tvSeeMore.setText(displayName + email + uid + photoUrl);
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

    private void initRcvCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Pizza", false));
        categoryList.add(new Category("drinks", false));
        categoryList.add(new Category("snacks", false));
        categoryList.add(new Category("sauce", false));
        categoryList.add(new Category("rice", false));
        categoryList.add(new Category("chicken", false));
        categoryList.add(new Category("potato", false));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(categoryList, this);
        categoryAdapter.callBackCategory(new CallBack.OnCategoryCallBack() {
            @Override
            public void onClick(String category) {
                cate = category;
                filterFoodByCategory(cate);
            }
        });
        filterFoodByCategory(categoryAdapter.setDefaultCheck());
        binding.rcvCategory.setLayoutManager(linearLayoutManager);
        binding.rcvCategory.setAdapter(categoryAdapter);
    }

    private void initFoodAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        foodAdapter = new FoodAdapter(this, filterList, idFood -> {
            Intent intent = new Intent(HomeActivity.this, FoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFood",idFood);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        binding.rcvFood.setLayoutManager(linearLayoutManager);
        binding.rcvFood.setAdapter(foodAdapter);

    }

    private void filterFoodByCategory(String cate) {
        filterList.clear();
        for (Food f: foodList) {
            if (f.getCategory().equals(cate)) {
                filterList.add(f);
            }
        }
        initFoodAdapter();
    }

    private void initLoadingData() {
        loadingDataDialog = new ProgressDialog(this);
        loadingDataDialog.setMessage("Dang tai data");
        loadingDataDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDataDialog.setCancelable(false);
        loadingDataDialog.show();
    }
}

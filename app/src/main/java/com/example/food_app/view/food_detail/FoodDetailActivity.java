package com.example.food_app.view.food_detail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food_app.helper.CallBack;
import androidx.annotation.NonNull;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityFoodDetailBinding;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends BaseActivity<ActivityFoodDetailBinding> {
    private int idFood;
    private List<Food> foodList = new ArrayList<>();
    private List<Food> favouriteList = new ArrayList<>();
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
                        if (food.getPhotoString().equals("local")) {
                            binding.imvFood.setImageResource(f.getPhoto());
                        } else {
                            Glide.with(this).load(food.getPhotoString()).into(binding.imvFood);
                        }
                        binding.tvNameFood.setText(f.getTitle());
                        binding.tvPrice.setText(formatCost((int) f.getPrice()));
                        binding.tvContent.setText(f.getDescription());
                    }
                }
            }
        });
        getListFavouriteFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {}
        });
    }

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnFarvourite.setOnClickListener(v -> {
            boolean isExist = false;
            for (Food f: favouriteList) {
                if (f.getId() == food.getId()) {
                    Toast.makeText(this, "Món nay đã có trong danh sách yêu thích của bạn",Toast.LENGTH_SHORT).show();
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                addFoodToFavouriteList();
            }
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            Log.d("food",food.getTitle());
            boolean isExist = false;
            for (Cart c: cartList) {
                if (c.getFood().getId() == food.getId()) {
                    Toast.makeText(this, "Sản phẩm này đã có trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                cartList.add(new Cart(food,1));
                rf.child("Cart").child(user != null ? user.getUid() : "").setValue(cartList).addOnCompleteListener(task -> {
                    Toast.makeText(this, "Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                });
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
            public void onCancelled(@NonNull DatabaseError error) {}
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

    private void addFoodToFavouriteList() {
        favouriteList.add(food);
        rf.child("Favourite").child(user != null ? user.getUid() : "").setValue(favouriteList).addOnCompleteListener(task -> {
            Toast.makeText(this, "Đã thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
        });
    }
    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    private String formatCost(int cost) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCost = decimalFormat.format(cost)+"đ";
        return formattedCost;
    }
}

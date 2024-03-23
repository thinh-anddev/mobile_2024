package com.example.food_app.view.food_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityFoodDetailBinding;
import com.example.food_app.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends BaseActivity<ActivityFoodDetailBinding> {
    private int idFood;
    @Override
    protected ActivityFoodDetailBinding setViewBinding() {
        return ActivityFoodDetailBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food(1,"Pizza",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(2,"thinh",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(3,"tan",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(4,"tin",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(5,"thuc",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(6,"tri",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(7,"com",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
        foodList.add(new Food(8,"tuan",8.0,"pizza","con","Delivered between monday aug and thursday 20 from 8pm to 91:32 pm", R.drawable.bg_splash,10));
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

    @Override
    protected void viewListener() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }
}

package com.example.food_app.view.home.adapter;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.R;
import com.example.food_app.databinding.ItemFoodBinding;
import com.example.food_app.model.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    List<Food> foodList = new ArrayList<>();
    private Context context;
    IFoodListener listener;

    public FoodAdapter(Context context, List<Food> foodList, IFoodListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        holder.bindData(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private String formatCost(int cost) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCost = decimalFormat.format(cost)+"đ";
        return formattedCost;
    }

    public interface IFoodListener {
        void onClick(int idFood);
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding binding;
        public FoodViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Food food) {
            if (food == null) {
                return;
            }

            if (food.getPhotoString().equals("local")) {
                binding.imvFood.setImageResource(food.getPhoto());
            } else {
                Uri uri = Uri.parse(food.getPhotoString());
                Glide.with(binding.getRoot().getContext()).load(uri).into(binding.imvFood);
            }

            binding.tvFood.setText(food.getTitle());

            binding.tvPrice.setText(formatCost((int) food.getPrice()));

            binding.getRoot().setOnClickListener(v -> {
                listener.onClick(food.getId());
            });
        }
    }
}

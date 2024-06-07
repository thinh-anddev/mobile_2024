package com.example.food_app.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodAdapter.FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        if (food == null) {
            return;
        }

        holder.imvFood.setImageResource(food.getPhoto());

        holder.tvFood.setText(food.getTitle());

        holder.tvPrice.setText(formatCost((int) food.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(food.getId());
        });
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
        TextView tvFood, tvPrice;
        ImageView imvFood;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFood = itemView.findViewById(R.id.tvFood);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imvFood= itemView.findViewById(R.id.imvFood);
        }
    }
}

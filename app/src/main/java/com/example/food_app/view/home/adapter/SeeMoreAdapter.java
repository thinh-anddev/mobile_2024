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

public class SeeMoreAdapter extends RecyclerView.Adapter<SeeMoreAdapter.SeeMoreViewHolder> {
    private List<Food> listFood = new ArrayList<>();
    private Context context;
    IFoodListener listener;
    public SeeMoreAdapter(Context context,List<Food> listFood, IFoodListener listener) {
        this.context = context;
        this.listFood = listFood;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SeeMoreAdapter.SeeMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_see_more,parent,false);
        return new SeeMoreAdapter.SeeMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeMoreAdapter.SeeMoreViewHolder holder, int position) {
        Food food = listFood.get(position);

        if (food == null) {
            return;
        }

        holder.tvTitle.setText(food.getTitle());
        holder.tvPrice.setText(formatCost((int) food.getPrice()));
        holder.imvFood.setImageResource(food.getPhoto());
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(food.getId());
        });

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    private String formatCost(int cost) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCost = decimalFormat.format(cost)+"đ";
        return formattedCost;
    }

    public interface IFoodListener {
        void onClick(int idFood);
    }

    public class SeeMoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvPrice;
        ImageView imvFood;
        public SeeMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imvFood = itemView.findViewById(R.id.imvFood);
        }
    }
}

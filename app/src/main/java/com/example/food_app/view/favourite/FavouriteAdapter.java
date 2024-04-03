package com.example.food_app.view.favourite;

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

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private List<Food> favouriteList = new ArrayList<>();
    IFavouriteListener listener;
    private Context context;

    public FavouriteAdapter(Context context, List<Food> favouriteList, IFavouriteListener listener) {
        this.context = context;
        this.favouriteList = favouriteList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_see_more,parent,false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Food food = favouriteList.get(position);

        if (food == null) {
            return;
        }

        holder.tvTitle.setText(food.getTitle());
        holder.tvPrice.setText(String.valueOf(food.getPrice()));
        holder.imvFood.setImageResource(food.getPhoto());
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(food.getId());
        });
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public interface IFavouriteListener {
        void onClick(int idFood);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvPrice;
        ImageView imvFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imvFood = itemView.findViewById(R.id.imvFood);
        }
    }
}

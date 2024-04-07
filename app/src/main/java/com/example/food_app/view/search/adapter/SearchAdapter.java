package com.example.food_app.view.search.adapter;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Food> listSearch = new ArrayList<>();
    private Context context;
    OnClickSearchListener listener;

    public SearchAdapter(Context context, List<Food> listSearch, OnClickSearchListener listener) {
        this.context = context;
        this.listSearch = listSearch;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Food food = listSearch.get(position);
        if (food == null) {
            return;
        }

        holder.imvFood.setImageResource(food.getPhoto());

        holder.tvFood.setText(food.getTitle());

        holder.tvPrice.setText(String.valueOf(food.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(food.getId());
        });
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    public interface OnClickSearchListener {
        void onClick(int id);
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imvFood;
        TextView tvFood, tvPrice;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFood = itemView.findViewById(R.id.tvFood);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imvFood= itemView.findViewById(R.id.imvFood);
        }
    }
}

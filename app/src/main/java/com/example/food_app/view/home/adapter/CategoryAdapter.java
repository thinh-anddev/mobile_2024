package com.example.food_app.view.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category == null) {
            return;
        }

        if (category.isCheck()) {
            holder.tvTitle.setTextColor(Color.parseColor("#FA4A0C"));
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setTextColor(Color.parseColor("#9A9A9D"));
            holder.view.setVisibility(View.INVISIBLE);
        }

        holder.tvTitle.setText(category.getTitle());

        holder.tvTitle.setOnClickListener(v -> {
            setCheck(category.getTitle());
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private void setCheck(String title) {
        for (Category c: categoryList) {
            c.setCheck(c.getTitle().equals(title));
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            view = itemView.findViewById(R.id.view);
        }
    }
}

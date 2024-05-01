package com.example.food_app.view.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.R;
import com.example.food_app.model.News;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    private Context context;
    private List<News> listNew;
    public NewAdapter(Context context, List<News> listNew) {
        this.context = context;
        this.listNew = listNew;
    }
    @NonNull
    @Override
    public NewAdapter.NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_view, parent, false);
        return new NewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapter.NewViewHolder holder, int position) {
        News model = listNew.get(position);
        Glide.with(holder.itemView.getContext()).load(model.getBanner()).into(holder.ivBanner);
        holder.tvTitle.setText(model.getTitle());
        holder.tvView.setText(model.getViews());
        holder.tvRate.setText(String.valueOf(model.getRate()));
        holder.itemView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.itemView.getRootView().getContext().startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listNew.size();
    }


    public class NewViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRate, tvView;
        ImageView ivBanner;
        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvView = itemView.findViewById(R.id.tvView);
            ivBanner = itemView.findViewById(R.id.ivBanner);
        }
    }
}

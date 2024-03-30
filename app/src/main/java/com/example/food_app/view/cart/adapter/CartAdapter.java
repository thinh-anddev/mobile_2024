package com.example.food_app.view.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import com.example.food_app.R;
import com.example.food_app.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Food> foodList = new ArrayList<>();
    private ViewBinder viewBinder = new ViewBinder();
    private Context context;
    IFoodListener listener;

    public CartAdapter(Context context, List<Food> foodList,IFoodListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Food food = foodList.get(position);

        if (food == null) {
            return;
        }

        viewBinder.bind(holder.swipeLayout,String.valueOf(food.getId()));
        holder.tvTitle.setText(food.getTitle());
        holder.tvPrice.setText(String.valueOf(food.getPrice()));
        holder.imvFood.setImageResource(food.getPhoto());

        holder.btnDelete.setOnClickListener(v -> {
            foodList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

        holder.btnFarvourite.setOnClickListener(v -> {
            listener.onClick(food.getId());
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface IFoodListener {
        void onClick(int idFood);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        LinearLayout llBonus;
        ImageView btnFarvourite,btnDelete,imvFood,btnSubtract,btnPlus;
        TextView tvTitle,tvPrice,tvCount;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            llBonus = itemView.findViewById(R.id.layoutBonus);
            btnFarvourite = itemView.findViewById(R.id.btnFarvourite);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imvFood = itemView.findViewById(R.id.imvFood);
            btnSubtract = itemView.findViewById(R.id.btnSubtract);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCount = itemView.findViewById(R.id.tvCount);
        }
    }
}

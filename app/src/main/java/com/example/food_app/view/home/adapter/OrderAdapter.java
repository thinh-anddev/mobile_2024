package com.example.food_app.view.home.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.example.food_app.model.Order;
import com.example.food_app.utils.Constant;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    Order order;
    public OrderAdapter(Context context, Order order) {
        this.context = context;
        this.order = order;
    }
    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Cart cart = order.getFoodList().get(position);
        Food food = cart.getFood();

        holder.tvCount.setText(cart.getNumber()+"x");
        holder.tvNameFood.setText(food.getTitle());
        double price = Double.valueOf(cart.getNumber()) * food.getPrice();
        holder.tvPrice.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {
        return order.getFoodList().size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView tvCount, tvPrice, tvNameFood;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvNameFood = itemView.findViewById(R.id.tvNameFood);
        }
    }
}

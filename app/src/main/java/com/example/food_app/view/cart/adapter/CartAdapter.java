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
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList = new ArrayList<>();
    private ViewBinder viewBinder = new ViewBinder();
    private Context context;
    IFoodListener listener;
    private int curCount;
    private int curPrice;
    public CartAdapter(Context context, List<Cart> cartList,IFoodListener listener) {
        this.context = context;
        this.cartList = cartList;
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
        Cart cart = cartList.get(position);
        Food food = cart.getFood();

        if (food == null) {
            return;
        }
        curPrice = Double.valueOf(food.getPrice()).intValue();
        curCount = cart.getNumber();
        viewBinder.bind(holder.swipeLayout,String.valueOf(food.getId()));
        holder.tvTitle.setText(food.getTitle());
        holder.tvPrice.setText(formatCost(curPrice));
        holder.imvFood.setImageResource(food.getPhoto());
        holder.tvCount.setText(String.valueOf(curCount));


        holder.btnPlus.setOnClickListener(v -> {
            curCount++;
            cart.setNumber(curCount);
            holder.tvCount.setText(String.valueOf(curCount));
            int cost = curCount * curPrice;
            holder.tvPrice.setText(formatCost(cost));
        });

        holder.btnSubtract.setOnClickListener(v -> {
            if (Integer.parseInt(holder.tvCount.getText().toString())  > 1) {
                curCount--;
                cart.setNumber(curCount);
                holder.tvCount.setText(String.valueOf(curCount));
                int cost = curCount * curPrice;
                holder.tvPrice.setText(formatCost(cost));
            }
        });


        holder.btnDelete.setOnClickListener(v -> {
            cartList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            listener.onDeleteFood();
        });

        holder.btnFarvourite.setOnClickListener(v -> {
            listener.onAddFar(food.getId());
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private String formatCost(int cost) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCost = decimalFormat.format(cost)+"đ";
        return formattedCost;
    }

    public interface IFoodListener {
        void onAddFar(int idFood);
        void onDeleteFood();
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

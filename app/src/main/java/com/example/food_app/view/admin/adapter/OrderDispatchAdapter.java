package com.example.food_app.view.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDispatchAdapter extends RecyclerView.Adapter<OrderDispatchAdapter.ViewHolder> {
    private Context context;
    private List<Order> orderList = new ArrayList<>();

    public OrderDispatchAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public OrderDispatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_dispatch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDispatchAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvNameUser.setText(order.getNameUser());
        holder.tvDate.setText(order.getTime());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvDate, tvAccept, tvDecline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAccept = itemView.findViewById(R.id.btnAccept);
            tvDecline = itemView.findViewById(R.id.btnDecline);
        }
    }
}

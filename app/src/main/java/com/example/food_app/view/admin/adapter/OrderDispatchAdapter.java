package com.example.food_app.view.admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDispatchAdapter extends RecyclerView.Adapter<OrderDispatchAdapter.ViewHolder> {
    private Context context;
    private List<Order> orderList = new ArrayList<>();
    private OnClickOrder listener;

    public OrderDispatchAdapter(Context context, List<Order> orderList, OnClickOrder listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
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

        holder.tvAccept.setOnClickListener(v -> {
            listener.onClick(order.getInvoiceNumber());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OnClickOrder {
        void onClick(String invoiceNumber);
    }
    public void updateOrderList(List<Order> orderList) {
        this.orderList.clear();
        this.orderList.addAll(orderList);
        notifyDataSetChanged();
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

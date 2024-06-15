package com.example.food_app.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Order;
import com.example.food_app.utils.Constant;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.HistoryViewHolder> {
    private Context context;
    private List<Order> orderList;
    onClickOrderListener listener;
    public HistoryOrderAdapter(Context context, List<Order> orderList, onClickOrderListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public HistoryOrderAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryOrderAdapter.HistoryViewHolder holder, int position) {
        Order order = orderList.get(position);

        String status = "";
        switch (order.getStatus()) {
            case Constant.AWAITING_PAYMENT:
                status = "Đang chờ thanh toán";
                break;
            case Constant.PENDING:
                status = "Đang giao hàng";
                break;
            case Constant.DELIVERED:
                status = "Đã giao hàng";
                break;
            case Constant.CANCELLED:
                status = "Đã hủy";
                break;
//            default:
//                binding.btnStartOrder.setText("Thanh toán");
        }

        holder.invoiceNumber.setText(order.getInvoiceNumber()+"("+status+")");
        holder.tvDate.setText(order.getTime());
        holder.tvPrice.setText(formatCost((int) countSumPrice(order)));

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    private double countSumPrice(Order order) {
        double sum = 0.0;
        for (Cart c: order.getFoodList()) {
            sum += Double.valueOf(c.getNumber()) * c.getFood().getPrice();
        }
        return sum;
    }
    private String formatCost(int cost) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedCost = decimalFormat.format(cost)+"đ";
        return formattedCost;
    }
    public interface onClickOrderListener {
        void onClick(Order order);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView invoiceNumber, tvDate, tvPrice;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            invoiceNumber = itemView.findViewById(R.id.invoiceNumber);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}

package com.example.food_app.view.admin.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.ViewHolder> {
    private List<User> users = new ArrayList<>();
    private Context context;
    OnClickOnButton listener;

    public UserManagerAdapter(Context context, List<User> users, OnClickOnButton listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }
    @NonNull
    @Override
    public UserManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_manager, parent, false);
        return new UserManagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserManagerAdapter.ViewHolder holder, int position) {
        User user = users.get(position);

        holder.tvNameUser.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvBlock.setText(user.isBlock() ? "Bỏ chặn" : "Chặn");

        holder.tvEdit.setOnClickListener(v -> {
            listener.onEdit(user.getId());
        });

        holder.tvBlock.setOnClickListener(v -> {
            listener.onBlock(user.getId());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public interface OnClickOnButton {
        void onEdit(String id);
        void onBlock(String id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvEmail, tvEdit, tvBlock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvEdit = itemView.findViewById(R.id.btnEdit);
            tvBlock = itemView.findViewById(R.id.btnBlock);
        }
    }
}

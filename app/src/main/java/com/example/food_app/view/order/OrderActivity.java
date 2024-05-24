package com.example.food_app.view.order;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.R;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityOrderBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.home.HomeActivity;
import com.example.food_app.view.home.adapter.OrderAdapter;
import com.example.food_app.view.profile.ChangeInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {
    private static final String CHANNEL_ID = "DTDFood";
    private static final int NOTIFICATION_ID = 1;
    private User currentUser = null;
    private Order order;
    private String actionOrder;
    private OrderAdapter orderAdapter;
    ProgressDialog progressDialog;

    @Override
    protected ActivityOrderBinding setViewBinding() {
        return ActivityOrderBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        createNotificationChannel();
        binding.imvTickCard.setActivated(true);
        initLoadingData();
        getUserFromFirebase(() -> {
            setUpProfile();
            progressDialog.cancel();
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            order = (Order) bundle.getSerializable("order");
            actionOrder = bundle.getString("actionOrder");
        }
        initViewOrder();
        initAdapter();
        bindEvent();
        binding.tvSumValue.setText(String.valueOf(countSumPrice(order)));
    }

    @Override
    protected void viewListener() {
    }

    private void bindEvent() {
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnStartOrder.setOnClickListener(v -> {
            sendNotification();
        });

        binding.cvProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChangeInfoActivity.class);
            intent.putExtra("INVOICE_NUMBER", order.getInvoiceNumber());
            intent.putExtra("FROM", "ORDER");
            startActivity(intent);
            finish();
        });

        binding.cancelOrder.setOnClickListener(v -> {
            order.setStatus(Constant.CANCELLED);
            updateOrder(order);
            Toast.makeText(this, "Đã hủy", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void initAdapter() {
        orderAdapter = new OrderAdapter(this, order);
        binding.rvFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvFood.setAdapter(orderAdapter);
    }

    private void initViewOrder() {
        switch (actionOrder) {
            case Constant.AWAITING_PAYMENT:
                binding.btnStartOrder.setVisibility(View.VISIBLE);
                binding.llButton.setVisibility(View.GONE);
                binding.cancelOrder.setVisibility(View.GONE);
                break;
            case Constant.PENDING:
                binding.btnStartOrder.setVisibility(View.GONE);
                binding.llButton.setVisibility(View.GONE);
                binding.cancelOrder.setVisibility(View.VISIBLE);
                break;
            case Constant.DELIVERED:
                binding.btnStartOrder.setVisibility(View.GONE);
                binding.llButton.setVisibility(View.VISIBLE);
                binding.cancelOrder.setVisibility(View.GONE);
                break;
            case Constant.CANCELLED:
                binding.btnStartOrder.setVisibility(View.GONE);
                binding.llButton.setVisibility(View.GONE);
                binding.cancelOrder.setVisibility(View.GONE);
                break;

        }
    }

    private void getUserFromFirebase(CallBack.OnDataLoad listener) {
        rf.child("Users").child(user != null ? user.getUid() : "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(User.class);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateOrder(Order order) {
        Log.d("getInvoiceNumber", order.getInvoiceNumber());
        rf.child("Order").child(user.getUid()).child(order.getInvoiceNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    rf.child("Order").child(user.getUid()).child(order.getInvoiceNumber()).setValue(order);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpProfile() {
        binding.tvName.setText(order.getNameUser());
        binding.tvEmail.setText(order.getEmail());
        binding.tvContact.setText(order.getContact());
        binding.tvAddress.setText(!order.getAddress().equals("") ? order.getAddress() : "Hãy cập nhật đỉa chỉ của bạn");
    }

    private double countSumPrice(Order order) {
        double sum = 0.0;
        for (Cart c : order.getFoodList()) {
            sum += Double.valueOf(c.getNumber()) * c.getFood().getPrice();
        }
        return sum;
    }

    private void initLoadingData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dang tai data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "con cac tan ngu";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Đặt thức ăn thành công")
                .setContentText("Chờ tí nhé! Shipper đang đến.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

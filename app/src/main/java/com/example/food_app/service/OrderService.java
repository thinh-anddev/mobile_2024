package com.example.food_app.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.example.food_app.utils.Constant;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.food_app.R;
import com.example.food_app.view.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class OrderService extends Service {
    private static final String CHANNEL_ID = "DTDFood";
    private static final int NOTIFICATION_ID = 1;
    protected FirebaseAuth mAuth;
    protected FirebaseUser user;
    protected FirebaseDatabase db;
    protected DatabaseReference rf;
    private Map<String, String> previousStatusMap;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        rf = db.getReference();

        previousStatusMap = new HashMap<>();

        rf.child("Order").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String orderInvoice = dataSnapshot.child("invoiceNumber").getValue(String.class);
                    String newStatus = dataSnapshot.child("status").getValue(String.class);
                    if (newStatus != null) {
                        String previousStatus = previousStatusMap.get(orderInvoice);
                        if (previousStatus == null || !previousStatus.equals(newStatus)) {
                            previousStatusMap.put(orderInvoice, newStatus);
                            notifyUser(orderInvoice, newStatus);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    private void notifyUser(String invoiceNumber, String status) {
        String notifyStatus = "";

        switch (status) {
            case Constant.AWAITING_PAYMENT:
                notifyStatus = "Hãy tiến hành thanh toán";
                break;
            case Constant.CANCELLED:
                notifyStatus = "Đơn hàng của bạn đã bị hủy";
                break;
            case Constant.PENDING:
                notifyStatus = "Chờ tí nhé! Shipper đang đến.";
                break;
            case Constant.DELIVERED:
                notifyStatus = "Đơn hàng của bạn đã được giao";
                break;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("Đơn hàng " + invoiceNumber)
                .setContentText(notifyStatus)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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
        notificationManager.notify(invoiceNumber.hashCode(), builder.build());
    }
}

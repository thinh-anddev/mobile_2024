package com.example.food_app.view.cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityCartBinding;
import com.example.food_app.helper.CallBack;
import com.example.food_app.model.Cart;
import com.example.food_app.model.Food;
import com.example.food_app.model.Order;
import com.example.food_app.model.User;
import com.example.food_app.utils.Constant;
import com.example.food_app.view.cart.adapter.CartAdapter;
import com.example.food_app.view.home.seemore.SeeMoreActivity;
import com.example.food_app.view.order.OrderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CartActivity extends BaseActivity<ActivityCartBinding> {
    private List<Cart> cartList = new ArrayList<>();
    private List<Food> favouriteList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Order> listOrderTemp = new ArrayList<>();
    private Order orderData;
    private User currentUser;
    private ProgressDialog dialogLoading;
    private CartAdapter cartAdapter;
    @Override
    protected ActivityCartBinding setViewBinding() {
        return ActivityCartBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        binding.tvClickHere.setText(Html.fromHtml("<font color=#FA4A0C>Nhấn vào đây </font> <font color=#000000>để đặt thức ăn thôi nào</font>"));
        initLoadingData();
        getUserFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {}
        });
        getListCartFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {
                dialogLoading.cancel();
                initCartAdapter();
                setUpCartScreen();
            }
        });
        getListFavouriteFromFirebase(new CallBack.OnDataLoad() {
            @Override
            public void onDataLoad() {}
        });

        initListener();
    }

    @Override
    protected void viewListener() {}
    private void initListener() {
        binding.btnBack.setOnClickListener(v -> {
            updateCartInFirebase();
            onBackPressed();
        });
        binding.tvClickHere.setOnClickListener(v -> {
            startActivity(new Intent(this, SeeMoreActivity.class));
            finish();
        });
        binding.btnClearAll.setOnClickListener(v -> {
            cartList.clear();
            updateCartInFirebase();
        });
        binding.btnCreateOrder.setOnClickListener(v -> {
            addManyItemProduct();
            buyManyItemProduct();
            Log.d("kkkk",listOrderTemp.size()+"");
        });
    }

    private void initLoadingData() {
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Dang tai data");
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    private void initCartAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartAdapter = new CartAdapter(this, cartList, new CartAdapter.IFoodListener() {
            @Override
            public void onAddFar(Food food) {
                boolean isExist = false;
                for (Food f: favouriteList) {
                    if (f.getId() == food.getId()) {
                        Toast.makeText(CartActivity.this, "Món nay đã có trong danh sách yêu thích của bạn",Toast.LENGTH_SHORT).show();
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    favouriteList.add(food);
                    addFoodToFavouriteList();
                }
            }
            @Override
            public void onDeleteFood() {
                setUpCartScreen();
            }

            @Override
            public void onOrder(Cart cart) {

            }
        });
        binding.rcvCart.setLayoutManager(linearLayoutManager);
        binding.rcvCart.setAdapter(cartAdapter);
    }

    private void addManyItemProduct() {
        orderData = new Order();
        List<Cart> listFoodTemp = new ArrayList<>();
        Iterator<Cart> iterator = cartList.iterator();
        while (iterator.hasNext()) {
            Cart c = iterator.next();
            if (c.isCheck()) {
                iterator.remove();
                listFoodTemp.add(c);
            }
        }

        Order order = new Order(
                currentUser.getName(),
                currentUser.getAddress(),
                currentUser.getContact(),
                currentUser.getEmail(),
                dateCurrent() + " " + timeCurrent(),
                listFoodTemp,
                Constant.NOT_CHECK_OUT,
                genUUID()
        );
        orderData = order;
    }

    private void buyManyItemProduct() {
        //buy
        rf.child("Order").child(user.getUid()).child(genUUID()).setValue(orderData).addOnCompleteListener(task -> {
            Toast.makeText(this, "Tạo đơn hàng", Toast.LENGTH_SHORT).show();
        });
        Intent intent = new Intent(this, OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("actionOrder", Constant.NOT_CHECK_OUT);
        bundle.putSerializable("order",orderData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getListCartFromFirebase(CallBack.OnDataLoad listener) {
        cartList.clear();
        rf.child("Cart").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Food f = dataSnapshot.child("food").getValue(Food.class);
                    int number = dataSnapshot.child("number").getValue(Integer.class);
                    cartList.add(new Cart(f,number));
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void updateCartInFirebase() {
        rf.child("Cart").child(user != null ? user.getUid() : "").setValue(cartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    private void setUpCartScreen() {
        if (cartList.size() == 0) {
            binding.llContent.setVisibility(View.GONE);
            binding.rcvCart.setVisibility(View.GONE);
            binding.clEmptyCart.setVisibility(View.VISIBLE);
        } else {
            binding.llContent.setVisibility(View.VISIBLE);
            binding.rcvCart.setVisibility(View.VISIBLE);
            binding.clEmptyCart.setVisibility(View.GONE);
        }
    }

    private void addFoodToFavouriteList() {
        rf.child("Favourite").child(user != null ? user.getUid() : "").setValue(favouriteList).addOnCompleteListener(task -> {
            Toast.makeText(this, "Đã thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
        });
    }

    private void getListFavouriteFromFirebase(CallBack.OnDataLoad listener) {
        favouriteList.clear();
        rf.child("Favourite").child(user != null ? user.getUid() : "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Food f = d.getValue(Food.class);
                    favouriteList.add(f);
                }
                listener.onDataLoad();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getUserFromFirebase(CallBack.OnDataLoad listener) {
        userList.clear();
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

    private String dateCurrent() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private String timeCurrent() {
        return new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());
    }

    private String genUUID() {
        // Tạo một UUID ngẫu nhiên
        UUID uuid = UUID.randomUUID();
        // Chuyển UUID thành chuỗi và lấy một phần của nó làm mã số hóa đơn
        String invoiceNumber = uuid.toString().replace("-", "").substring(0, 12).toUpperCase();
        return invoiceNumber;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        updateCartInFirebase();
//    }
}

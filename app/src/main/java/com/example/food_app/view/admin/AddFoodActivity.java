package com.example.food_app.view.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityAddFoodBinding;
import com.example.food_app.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

public class AddFoodActivity  extends BaseActivity<ActivityAddFoodBinding> {
    private DatabaseReference foodRef;
    private ProgressDialog dialog;
    @Override
    protected ActivityAddFoodBinding setViewBinding() {
        return ActivityAddFoodBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        foodRef = FirebaseDatabase.getInstance().getReference("Foods");
        initLoadingData();
    }

    @Override
    protected void viewListener() {
        binding.btnAddFood.setOnClickListener(v -> {
            addFood();
        });
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void addFood() {
        dialog.show();
        String title = binding.edtName.getText().toString();
        String category = binding.edtCate.getText().toString();
        String description = binding.edtDescrip.getText().toString();
        String priceStr = binding.edtPrice.getText().toString();
        String quantityStr = binding.edtQuantity.getText().toString();
        int photo = 1;

        if (title.isEmpty() || category.isEmpty() || description.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }


        int price = Integer.parseInt(priceStr);
        int quantity = Integer.parseInt(quantityStr);


        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentItemCount = (int) dataSnapshot.getChildrenCount();


                int newId = currentItemCount + 1;


                Food food = new Food(newId, title, category, price, quantity, photo, description);


                foodRef.child(String.valueOf(newId)).setValue(food)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddFoodActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddFoodActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               
                Log.e("AddFoodActivity", "Failed to retrieve data: " + databaseError.getMessage());
                Toast.makeText(AddFoodActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    hideKeyboard(this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }
}

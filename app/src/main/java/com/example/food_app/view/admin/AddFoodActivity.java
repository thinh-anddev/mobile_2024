package com.example.food_app.view.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.Manifest;
import com.bumptech.glide.Glide;
import com.example.food_app.base.BaseActivity;
import com.example.food_app.databinding.ActivityAddFoodBinding;
import com.example.food_app.model.Food;
import com.example.food_app.utils.CommonUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import android.widget.Toast;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class AddFoodActivity extends BaseActivity<ActivityAddFoodBinding> {
    private DatabaseReference foodRef;
    private ProgressDialog dialog;
    private static final String[] paths = {"Pizza", "drinks", "potato", "snacks", "sauce", "rice", "chicken"};
    private ArrayAdapter ad;
    String category;

    @Override
    protected ActivityAddFoodBinding setViewBinding() {
        return ActivityAddFoodBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        foodRef = db.getReference("Foods");
        initLoadingData();

        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, paths);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.coursesspinner.setAdapter(ad);
    }

    @Override
    protected void viewListener() {
        binding.btnAddFood.setOnClickListener(v -> {
            addFood();
        });
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.fImage.setOnClickListener(v -> {
            checkPermissionCamera();
        });
        binding.coursesspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
                Log.d("category",category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addFood() {
        dialog.show();
        String title = binding.edtName.getText().toString();
        String description = binding.edtDescrip.getText().toString();
        String priceStr = binding.edtPrice.getText().toString();
        String quantityStr = binding.edtQuantity.getText().toString();
        String image = binding.edtImage.getText().toString().trim();
        Log.d("imageFirebase", image+"---");

        if (title.isEmpty() || category.isEmpty() || description.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()
                || image.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentItemCount = (int) dataSnapshot.getChildrenCount();
                int newId = currentItemCount + 1;
                Food food = new Food(newId, title, price, category, "con", description, 0, quantity, image);
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

    private void checkPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 1222);
            } else {
                selectImageFromGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1222);
            } else {
                selectImageFromGallery();
            }
        }
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                binding.edtImage.setText(data != null ? data.getData().toString() : null);
                Glide.with(this).load(data.getData().toString()).into(binding.fImageChild);
                binding.fImageChild.setVisibility(View.VISIBLE);
                Log.d("imageActi", data.getData().toString()+"---");
            }
        }
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
                    CommonUtils.hideSoftKeyboard(this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initLoadingData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng đợi");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }
}

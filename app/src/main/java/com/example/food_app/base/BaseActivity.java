package com.example.food_app.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.food_app.model.Cheating;
import com.example.food_app.utils.Constant;
import com.example.food_app.utils.SharePreferenceUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;

    protected abstract T setViewBinding();

    //setupView here
    protected abstract void initView();

    //listen to user action here
    protected abstract void viewListener();
    protected FirebaseAuth mAuth;
    protected FirebaseUser user;
    protected FirebaseDatabase db;
    protected DatabaseReference rf;
    protected String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = setViewBinding();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        rf = db.getReference();
        SharePreferenceUtils.init(this);
        setContentView(binding.getRoot());
        initWindow();
        fullScreenCall();
        initView();
        viewListener();
    }

    public void initWindow() {
        Window window = getWindow();
        if (window != null) {
            Drawable background = new ColorDrawable(Color.parseColor("#FFFFFF"));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void fullScreenCall() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            fullScreenImmersive(getWindow());
        }
    }

    private void fullScreenImmersive(Window window) {
        if (window != null) {
            fullScreenImmersive(window.getDecorView());
        }
    }

    private void fullScreenImmersive(View view) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }
    public void readJsonToObjectFromUrl(String urlJsonMega) {
        Runnable myRunnable = new Runnable() {
            private Handler myHandler = getHandler();

            @Override
            public void run() {
                StringBuilder content = new StringBuilder();
                try {
                    URL myUrl = new URL(urlJsonMega);
                    HttpURLConnection urlConnection = (HttpURLConnection) myUrl.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    inputStream.close();
                    String str = content.toString();
                    Message msg = myHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = str;
                    myHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Xử lý ngoại lệ ở đây nếu cần
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();
    }

    private Handler getHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message stringJson) {
                if (stringJson.what == 0) {
                    String dataJson = (String) stringJson.obj;
                    Gson gson = new Gson();
                    Cheating objectMegaApp = gson.fromJson(dataJson, Cheating.class);
                    if (objectMegaApp != null && objectMegaApp.getCheating() != null) {
                        SharePreferenceUtils.getString(Constant.CHEATING, objectMegaApp.getCheating());
                    }
                }
            }
        };
    }
}

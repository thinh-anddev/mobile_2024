package com.example.food_app.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

public class CommonUtils {

    public static void hideSoftKeyboard(@NonNull Activity activity) {
        //hide keyboard when click close icon
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;

            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            if (actNw == null) return false;

            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true;
            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true;
            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true;
            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) return true;
        }

        return false;
    }
}

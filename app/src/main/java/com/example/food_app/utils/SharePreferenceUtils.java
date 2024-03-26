package com.example.food_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePreferenceUtils {
    private static SharedPreferences mSharePref;
    public static void init(Context context) {
        if (mSharePref == null) {
            mSharePref = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    private static SharedPreferences.Editor editor() {
        return mSharePref.edit();
    }

    public static void putLong(String key, long value) {
        editor().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return mSharePref.getLong(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        editor().putBoolean(key,value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mSharePref.getBoolean(key,defaultValue);
    }

    public static void putString(String key, String value) {
        editor().putString(key,value);
    }

    public static String getString(String key, String defaultValue) {
        return mSharePref.getString(key,defaultValue);
    }

}

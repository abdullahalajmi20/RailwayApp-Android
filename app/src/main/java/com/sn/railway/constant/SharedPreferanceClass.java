package com.sn.railway.constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sn.railway.R;


public class SharedPreferanceClass {

    public static String LOGIN = "login";// for save login object


    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
    }


    public static String getString(Context context, String key) {

        return getPrefs(context).getString((key), "");
    }

    public static void setString(Context context, String key, String value) {

        getPrefs(context).edit().putString(key, (value)).commit();
    }


    public static void setInt(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }


    public static void setBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    public static void setCustomObject(Context context, String key, Object object) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(key, (json));
        prefsEditor.commit();
    }

    public static Object getCustomObject(Context context, String key, Object object) {
        Gson gson = new Gson();
        String json = getPrefs(context).getString(key, "");
        Object obj = gson.fromJson((json), object.getClass());
        return obj;
    }
}
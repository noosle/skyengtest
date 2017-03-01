package com.skyeng.skyengtest.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static String TOKEN = "token";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs;



    public static void set(String fieldName, String value, Context context) {
        if (editor == null)
            editor = android.preference.PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(fieldName, value);
        editor.apply();
    }


    public static String get(String fieldName, Context context) {
        if (prefs == null)
            prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(fieldName, "");
    }


}

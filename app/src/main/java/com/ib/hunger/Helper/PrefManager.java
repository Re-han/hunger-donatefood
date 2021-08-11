package com.ib.hunger.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
    }

    public static void insertData(Context context, String key, String value) {
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getStr(Context context,String key) {
        return getPrefs(context).getString(key,null);
    }




    public static SharedPreferences getDPrefs(Context context){
        return context.getSharedPreferences("DLoginDetails",Context.MODE_PRIVATE);
    }

    public static void insertDData(Context context, String key, String value) {
        SharedPreferences.Editor editor=getDPrefs(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getDStr(Context context,String key) {
        return getDPrefs(context).getString(key,null);
    }
}

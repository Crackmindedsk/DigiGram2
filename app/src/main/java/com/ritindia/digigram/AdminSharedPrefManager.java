package com.ritindia.digigram;

import android.content.Context;
import android.content.SharedPreferences;

public class AdminSharedPrefManager {
    private static AdminSharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedprefadmin";
    private static final String KEY_USERNAME = "adminname";
    private static final String KEY_ADMIN_LEVEL = "alevel";
    private static final String KEY_ADMIN_NAME = "aname";
    private static final String KEY_ADMIN_PHONE = "phone";




    private AdminSharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized AdminSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AdminSharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean adminLogin(int alevel, String aname, String phone){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ADMIN_LEVEL, String.valueOf(alevel));
        editor.putString(KEY_ADMIN_NAME, aname);
        editor.putString(KEY_ADMIN_PHONE, phone);

        editor.apply();

        return true;
    }

    public String getAdminLevel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_LEVEL, null);
    }
    public String getAdminName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_NAME, null);
    }
    public String getAdminPhone(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_PHONE, null);
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ADMIN_NAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}

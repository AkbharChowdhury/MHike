package com.mhike.m_hike.classes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mhike.m_hike.LoginActivity;

public final class AccountPreferences {

    public static final String LOGIN_SHARED_PREF =  "LoginDetails";
    public static final String USERID =  "UserID";

    private AccountPreferences(){

    }

    public static void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AccountPreferences.USERID, 0);
        editor.apply();
        // redirect to login page
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    public static void setLoginShredPref(Context context, int userID) {

        SharedPreferences loginSharedPref = context.getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPref.edit();
        editor.putInt(AccountPreferences.USERID, userID);
        editor.apply();
    }


}

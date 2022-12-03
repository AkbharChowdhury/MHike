package com.mhike.m_hike.classes.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.HikeJson;


import java.util.List;

public final class User {
    private TextInputLayout txtFirstName;
    private TextInputLayout txLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<HikeJson> detailList;
    private int userID;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TextInputLayout getTxtFirstName() {
        return txtFirstName;
    }

    public void setTxtFirstName(TextInputLayout txtFirstName) {
        this.txtFirstName = txtFirstName;
    }

    public TextInputLayout getTxLastName() {
        return txLastName;
    }

    public void setTxLastName(TextInputLayout txLastName) {
        this.txLastName = txLastName;
    }

    public TextInputLayout getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(TextInputLayout txtEmail) {
        this.txtEmail = txtEmail;
    }

    public TextInputLayout getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(TextInputLayout txtPassword) {
        this.txtPassword = txtPassword;
    }


    public User() {
    }

    // register form
    public User(TextInputLayout txtFirstName, TextInputLayout txLastName, TextInputLayout txtEmail, TextInputLayout txtPassword) {
        this.txtFirstName = txtFirstName;
        this.txLastName = txLastName;
        this.txtEmail = txtEmail;
        this.txtPassword = txtPassword;
    }

    // login form
    public User(TextInputLayout txtEmail, TextInputLayout txtPassword) {
        this.txtEmail = txtEmail;
        this.txtPassword = txtPassword;
    }
    public static int getUserID(Context context){
        SharedPreferences preferences = context.getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, context.MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }
    public static void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AccountPreferences.USERID, 0);
        editor.apply();
    }


    public User(int userID,List<HikeJson> userHikes) {
        this.detailList = userHikes;
        this.userID = userID;

    }
}

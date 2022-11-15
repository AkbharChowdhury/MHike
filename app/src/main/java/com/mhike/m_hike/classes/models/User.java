package com.mhike.m_hike.classes.models;

import com.google.android.material.textfield.TextInputLayout;

public final class User {
    private TextInputLayout txtFirstName;
    private TextInputLayout txLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

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
}

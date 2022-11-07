package com.mhike.m_hike;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.Encryption;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.User;
import com.mhike.m_hike.classes.Validation;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private DatabaseHelper db;
    private Validation form;
    private TextView lblLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.btn_login);
        context = getApplicationContext();
        checkRegisterMsg();
        form = new Validation(context);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btn_register);
        login.setOnClickListener(view -> handleLogin());
        db = DatabaseHelper.getInstance(context);
        handleRegisterLink();
        loginRequiredMsg();
        lblLoginError = findViewById(R.id.lbl_login_error);
        lblLoginError.setVisibility(View.INVISIBLE);


    }

    private void checkRegisterMsg() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("successRegister")) {
            Helper.longToastMessage(context, getString(R.string.register_success));
            extras.remove("successRegister");

        }
    }

    private void loginRequiredMsg() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("loginRequired")) {
            Helper.longToastMessage(context, "you must be logged in!");
            extras.remove("loginRequired");

        }
    }


    private void handleLogin() {

        String email = Helper.trimStr(txtEmail);
        String password = Helper.trimStr(txtPassword, false);

        if (form.validateLoginForm(new User(txtEmail, txtPassword))) {

            if (db.isAuthorised(email, Encryption.encode(password))) {
                lblLoginError.setVisibility(View.INVISIBLE);

                configUserDetails();
                return;
            }
            lblLoginError.setVisibility(View.VISIBLE);


        }

    }

    private void configUserDetails() {
        int userID = db.getUserID();
        AccountPreferences.setLoginShredPref(context, userID);
        startActivity(new Intent(context, MainActivity.class));
    }

    private void handleRegisterLink() {
        TextView registerLink = findViewById(R.id.btn_register_link);
        registerLink.setOnClickListener(view -> startActivity(new Intent(context, RegisterActivity.class)));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
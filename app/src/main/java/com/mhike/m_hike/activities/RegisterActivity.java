package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.models.User;
import com.mhike.m_hike.utilities.Validation;


public class RegisterActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = RegisterActivity.this;
    private Context context;
    private DatabaseHelper db;

    private TextInputLayout txtFirstName;
    private TextInputLayout txtLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    Validation form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.btn_register);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        form = new Validation(context, db);

        findTextFields();

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(view -> handleRegister());
    }

    private void findTextFields() {
        txtFirstName = findViewById(R.id.txtFirstname);

        txtLastName = findViewById(R.id.txtLastname);

        txtEmail = findViewById(R.id.txtEmail);

        txtPassword = findViewById(R.id.txtPassword);
    }

    private User getUserDetails() {
        String firstname = Helper.trimStr(txtFirstName);
        String lastname = Helper.trimStr(txtLastName);
        String email = Helper.trimStr(txtEmail);
        String password = Helper.trimStr(txtPassword, false);

        User userDetails = new User();
        userDetails.setFirstname(firstname);
        userDetails.setLastname(lastname);
        userDetails.setEmail(email);
        userDetails.setPassword(password);
        return userDetails;

    }

    private void handleRegister() {
        Helper.SetRedirectMessage(CURRENT_ACTIVITY, LoginActivity.class, getString(R.string.register_success));
        User user = new User(txtFirstName, txtLastName, txtEmail, txtPassword);

        // if the form is valid and user email is unique store their details in the database
        if (form.validateRegisterForm(user)) {
            if (db.registerUser(getUserDetails())) {
                Helper.SetRedirectMessage(CURRENT_ACTIVITY, LoginActivity.class, getString(R.string.register_success));
            }

            Helper.longToastMessage(context, getString(R.string.insertion_error));
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
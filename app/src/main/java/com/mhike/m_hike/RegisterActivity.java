package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.User;
import com.mhike.m_hike.classes.Validation;


public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout txtFirstName;
    private TextInputLayout txtLastName;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    Context context;
    DatabaseHelper db;
    Validation form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.btn_register);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        form = new Validation(context, db);

        txtFirstName = findViewById(R.id.txtFirstname);

        txtLastName = findViewById(R.id.txtLastname);

        txtEmail = findViewById(R.id.txtEmail);

        txtPassword = findViewById(R.id.txtPassword);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(view -> handleRegister());
    }

    private User getUserDetails() {
        String firstname = Helper.trimStr(txtFirstName);
        String lastname =  Helper.trimStr(txtLastName);
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
        User user = new User(txtFirstName, txtLastName, txtEmail, txtPassword);

        // if the form is valid and user email is unique store their details in the database
        if (form.validateRegisterForm(user)) {

            if (db.registerUser(getUserDetails())) {
                homePage();
                return;
            }

            Helper.longToastMessage(context, "there was an error creating you account. please try again later.");
        }

    }

    private void homePage() {

        txtFirstName.getEditText().setText("");
        txtLastName.getEditText().setText("");
        txtEmail.getEditText().setText("");
        txtPassword.getEditText().setText("");
        Helper.longToastMessage(context, "thank you for creating an account with us");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
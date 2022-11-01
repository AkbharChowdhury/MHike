package com.mhike.m_hike.classes;

import android.content.Context;
import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.tables.UserTable;


public final class Validation {
    private final Context context;
    private DatabaseHelper db;


    public Validation(Context context) {
        this.context = context;

    }

    public Validation(Context context, DatabaseHelper db) {
        this.context = context;
        this.db = db;


    }
//


    private boolean isValidEmail(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String email = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_EMAIL);

        if (email.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setError(textField, getInvalidEmailError());
            return false;
        }

        textField.setError(null);
        return true;
    }


    private boolean isValidEmailReg(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String email = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_EMAIL);

        if (email.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setError(textField, getInvalidEmailError());
            return false;
        }

        if (db.emailExists(email)) {
            setError(textField, getEmailExistsError());
            return false;
        }

        textField.setError(null);
        return true;
    }


    private void setError(TextInputLayout textField, String errorMessage) {
        textField.setError(errorMessage);

    }

    private boolean isMinLength(String str, int length) {
        return str.length() >= length;
    }


    private boolean isValidPassword(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String password = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_PASSWORD);

        if (password.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        textField.setError(null);
        return true;
    }

    private boolean isPassword8Chars(TextInputLayout textField) {

        String password = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_PASSWORD);


        if (password.isEmpty()) return true;
        int PASSWORD_MIN_LENGTH = 8;
        if (!isMinLength(password, PASSWORD_MIN_LENGTH)) {
            setError(textField, getPasswordMinLengthError(fieldName, PASSWORD_MIN_LENGTH));
            return false;
        }

        textField.setError(null);
        return true;
    }


    private boolean isValidFirstName(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String firstname = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_FIRSTNAME);

        if (firstname.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        if (!isValidName(firstname)) {
            setError(textField, getNameError(fieldName));
            return false;
        }

        textField.setError(null);
        return true;
    }

    private boolean isValidLastName(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String lastname = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(UserTable.COLUMN_LASTNAME);

        if (lastname.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        if (!isValidName(lastname)) {
            setError(textField, getNameError(fieldName));
            return false;
        }

        textField.setError(null);
        return true;
    }

    private String getNameError(String fieldName) {
        return String.format(context.getString(R.string.required_name_error), fieldName);
    }

    private String getRequiredFieldError(String fieldName) {
        return String.format(context.getString(R.string.required_field_error), fieldName);
    }

    private String getPasswordMinLengthError(String fieldName, int minLength) {
        return String.format(context.getString(R.string.password_min_error), fieldName, minLength);
    }


    private String getEmailExistsError() {
        return context.getString(R.string.email_exists_error);
    }


    private String getInvalidEmailError() {
        return context.getString(R.string.invalid_email_error);

    }


    public boolean validateRegisterForm(User user) {

        TextInputLayout txtFirstName = user.getTxtFirstName();
        TextInputLayout txtLastName = user.getTxLastName();
        TextInputLayout txtEmail = user.getTxtEmail();
        TextInputLayout txtPassword = user.getTxtPassword();

        return !(!isValidFirstName(txtFirstName) |
                !isValidLastName(txtLastName) |
                !isValidEmailReg(txtEmail) |
                !isValidPassword(txtPassword) |
                !isPassword8Chars(txtPassword)
        );

    }


    public boolean validateLoginForm(User user) {

        TextInputLayout txtEmail = user.getTxtEmail();
        TextInputLayout txtPassword = user.getTxtPassword();
        return !(!isValidEmail(txtEmail) | !isValidPassword(txtPassword));

    }


    /**
     * isValidName()
     * ^ beginning of the string
     * [A-Za-z] search for alphabetical chars either they are CAPITALS or not
     * + string contains at least one alphabetical char
     * $ end of the string
     */
    private boolean isValidName(String name) {
        return name.matches("^[A-Za-z]+$");
    }


}

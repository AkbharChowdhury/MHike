package com.mhike.m_hike.classes;

import android.content.Context;
import android.util.Patterns;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.tables.HikeTable;
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

    private void setDropDownError(AutoCompleteTextView dropdown, String errorMessage) {
        dropdown.setError(errorMessage);

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


    private String getDistanceFieldError(String fieldName, int minDistance) {
        return String.format(context.getString(R.string.distance_error), fieldName, minDistance);
    }

    private String getElevationFieldError(String fieldName, int minDistance) {
        return String.format(context.getString(R.string.elevation_error), fieldName, minDistance);
    }

    private String getElevationHighFieldError(String fieldName, String elevation) {
        return String.format(context.getString(R.string.high_high_error), fieldName, elevation);
    }


    private String getDurationFieldError(String fieldName, int minDuration) {
        return String.format(context.getString(R.string.distance_error), fieldName, minDuration);
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


    private boolean isValidDropdown(AutoCompleteTextView dropdown, String column) {
        String fieldName = Helper.capitalise(column);

        if (dropdown.getText().toString().isEmpty()) {
            setDropDownError(dropdown, getRequiredFieldError(fieldName));
            return false;
        }

        dropdown.setError(null);
        return true;
    }


    private boolean isValidHikeName(TextInputLayout textField) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String hikeName = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(HikeTable.COLUMN_Hike_NAME);

        if (hikeName.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        textField.setError(null);
        return true;
    }

    private boolean isValidDistance(TextInputLayout textField) {
        String distanceStr = Helper.trimStr(textField);

        String fieldName = Helper.capitalise(HikeTable.COLUMN_DISTANCE);
        final int minDistance = 2;
        if (distanceStr.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }
        if (Double.parseDouble(distanceStr) < minDistance) {
            setError(textField, getDistanceFieldError(fieldName, minDistance));
            return false;

        }

        textField.setError(null);
        return true;
    }


    private boolean isValidDuration(TextInputLayout textField) {
        String durationStr = Helper.trimStr(textField);

        String fieldName = Helper.capitalise(HikeTable.COLUMN_DISTANCE);
        final int minDuration = 30;


        if (durationStr.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }
        if (Double.parseDouble(durationStr) < minDuration) {
            setError(textField, getDurationFieldError(fieldName, minDuration));
            return false;

        }

        textField.setError(null);
        return true;
    }


    private boolean isValidElevation(TextInputLayout textField) {
        String elevationGainStr = Helper.trimStr(textField);

        String fieldName = Helper.capitalise(HikeTable.COLUMN_ELEVATION_GAIN);
        final int minElevationGain = 100;


        if (elevationGainStr.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }
        if (Double.parseDouble(elevationGainStr) < minElevationGain) {
            setError(textField, getElevationFieldError(fieldName, minElevationGain));
            return false;

        }

        textField.setError(null);
        return true;
    }

    private boolean isValidHigh(TextInputLayout textField, TextInputLayout elevationGain, AutoCompleteTextView txtDifficulty) {
        String highStr = Helper.trimStr(textField);
        String elevationGainStr = Helper.trimStr(elevationGain);


        String fieldName = Helper.capitalise(HikeTable.COLUMN_HIGH);


        if (highStr.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }
        if (!elevationGainStr.isEmpty() && Double.parseDouble(highStr) < Double.parseDouble(elevationGainStr)) {
            setError(textField, getElevationHighFieldError(fieldName, HikeTable.COLUMN_ELEVATION_GAIN));
            return false;

        }

        textField.setError(null);

        // calculate the difficulty based on the elevation and high
//        txtDifficulty.setText("Easy");

        return true;
    }


    private boolean isEmpty(TextInputLayout textField, String column) {
        // https://www.youtube.com/watch?v=veOZTvAdzJ8
        String field = Helper.trimStr(textField);
        String fieldName = Helper.capitalise(column);

        if (field.isEmpty()) {
            setError(textField, getRequiredFieldError(fieldName));
            return false;
        }

        textField.setError(null);
        return true;
    }


    public boolean validateHikeForm(Hike hike) {

        AutoCompleteTextView txtHikeDate = hike.getTxtHikeDate();
        TextInputLayout txtHikeName = hike.getTxtHikeName();
        TextInputLayout txtDescription = hike.getTxtDescription();
        TextInputLayout txtLocation = hike.getTxtLocation();
        TextInputLayout txtDistance = hike.getTxtDistance();
        TextInputLayout txtDuration = hike.getTxtDuration();
        AutoCompleteTextView txtParking = hike.getTxtParking();
        TextInputLayout txtElevationGain = hike.getTxtElevationGain();
        TextInputLayout txtHigh = hike.getTxtHigh();
        AutoCompleteTextView txtDifficulty = hike.getTxtDifficulty();

        return !(
                !isValidDropdown(txtHikeDate, HikeTable.COLUMN_HIKE_DATE) |
                        !isValidHikeName(txtHikeName) |
                        !isEmpty(txtDescription, HikeTable.COLUMN_DESCRIPTION) |
                        !isEmpty(txtLocation, HikeTable.COLUMN_LOCATION) |
                        !isValidDistance(txtDistance) |
                        !isValidDuration(txtDuration) |
                        !isValidDropdown(txtParking, HikeTable.COLUMN_PARKING_ID) |
                        !isValidElevation(txtElevationGain) |
                        !isValidHigh(txtHigh, txtElevationGain, txtDifficulty) |

                        !isValidDropdown(txtDifficulty, HikeTable.TABLE_NAME)


        );


//
//        TextInputLayout txtEmail = hike.getDescription();
//        TextInputLayout txtPassword = user.getTxtPassword();
//        return !(!isValidEmail(txtEmail) | !isValidPassword(txtPassword));

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

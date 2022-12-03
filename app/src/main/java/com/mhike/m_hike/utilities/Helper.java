package com.mhike.m_hike.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.enums.ActivityForm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ValueRange;

import android.content.Intent;

public final class Helper {
    public static final String TOAST_MESSAGE = "message";
    private static final String INTENT_MESSAGE = "message";


    private Helper() {
    }

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static String capitalise(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String trimStr(TextInputLayout textField) {
        return textField.getEditText().getText().toString().trim();

    }

    public static String trimStr(TextInputLayout textField, boolean isTrimmed) {
        return textField.getEditText().getText().toString();

    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();

    }

    public static String formatDate(String date) {
        String[] dateSplit = date.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);
        LocalDate selectedDate = LocalDate.of(year, month, day);
        String fullDay = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(selectedDate);
        return fullDay;
    }

    public static String formatTime(String time) {
        String[] timeSplit = time.split(":");
        String timeColonPattern = "hh:mm a";

        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);
        LocalTime colonTime = LocalTime.of(hour, minute);
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        return timeColonFormatter.format(colonTime);

    }

    public static void showActivityFormErrorMessage() {
        // display error and show valid enum Activity forms
        ActivityForm[] activityForms = ActivityForm.values();
        Log.d("invalidActivityForm", "You must enter a valid Activity form, from the following list");
        for (ActivityForm form : activityForms) {
            Log.d("validActivityForm", form.toString());
        }
    }

    public static void goToPage(Activity currentActivity, Class<? extends Activity> activityPageToOpen) {

        currentActivity.startActivity(new Intent(currentActivity, activityPageToOpen));

    }

    public static void SetRedirectMessage(Activity currentActivity, Class<? extends Activity> activityPageToOpen, String message) {
        Intent intent = new Intent(currentActivity, activityPageToOpen);
        intent.putExtra(INTENT_MESSAGE, message);
        currentActivity.startActivity(intent);
    }
    public static void getIntentMessage(Context context, Bundle extras){
        if (extras != null && extras.getString(Helper.INTENT_MESSAGE) !=null){
            Helper.longToastMessage(context, extras.getString(Helper.INTENT_MESSAGE));
            extras.clear();
        }

    }



    public static String formatDateShort(String date) {
        String[] dateSplit = date.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);
        LocalDate selectedDate = LocalDate.of(year, month, day);
        String fullDay = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(selectedDate);
        return fullDay;
    }

    private static double calcElevationPerMile(double distance, double elevationGain) {
        return elevationGain / (distance / 2);

    }


    public static int getDifficultyLevel(double distance, double elevationGain) {
        double total = calcElevationPerMile(distance, elevationGain);
        long result = (long) total;
        if (ValueRange.of(200, 400).isValidIntValue(result)) {
            return 0; // Easy
        }

        if (ValueRange.of(400, 700).isValidIntValue(result)) {
            return 1; // Moderate
        }
        if (ValueRange.of(700, 1000).isValidIntValue(result)) {
            return 2; // Hard
        }

        // 1_000+
        return 3; // Expert

    }


    public static int getDifficultyLevel2(double distance, double elevationGain) {
        double total = calcElevationPerMile(distance, elevationGain);
        long result = (long) total;
        if (ValueRange.of(200, 400).isValidIntValue(result)) {
            return 0; // Easy
        }

        if (ValueRange.of(400, 700).isValidIntValue(result)) {
            return 1; // Moderate
        }
        if (ValueRange.of(700, 1000).isValidIntValue(result)) {
            return 2; // Hard
        }

        // 1_000+
        return 3; // Expert

    }

    public static int getDifficultyColour(int difficultyLevel, Context c) {
        switch (difficultyLevel) {
            case 0:
                return c.getColor(R.color.green);
            case 1:
                return c.getColor(R.color.orange);

            default:
                return c.getColor(R.color.red);


        }
    }


    public static String removeFirstAndLastCharacter(String str) {
        return str.substring(1, str.length() - 1);
    }


}
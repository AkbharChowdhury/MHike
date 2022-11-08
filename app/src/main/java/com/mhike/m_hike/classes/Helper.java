package com.mhike.m_hike.classes;

import static android.content.Intent.getIntent;

import android.app.DirectAction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ValueRange;
import android.content.Intent;

public final class Helper {

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

    public static LocalDate getCurrentDate(){
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



    public static String formatDateShort(String date) {
        String[] dateSplit = date.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);
        LocalDate selectedDate = LocalDate.of(year, month, day);
        String fullDay = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(selectedDate);
        return fullDay;
    }
    private static double calcElevationPerMile(double distance, double elevationGain){
        return elevationGain/  (distance /2 ) ;

    }



    public static int getDifficultyLevel(double distance, double elevationGain){
        double total =  calcElevationPerMile(distance, elevationGain);
        long result = (long)total;
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


    public static int getDifficultyLevel2(double distance, double elevationGain){
        double total =  calcElevationPerMile(distance, elevationGain);
        long result = (long)total;
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

    public static int getDifficultyColour(int difficultyLevel, Context c){
        switch (difficultyLevel) {
            case 0:
                return c.getColor(R.color.green);
            case 1:
                return c.getColor(R.color.orange);

            default:
                return c.getColor(R.color.red);


        }
    }







}
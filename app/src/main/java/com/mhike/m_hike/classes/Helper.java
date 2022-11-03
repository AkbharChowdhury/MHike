package com.mhike.m_hike.classes;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.temporal.ValueRange;

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





}
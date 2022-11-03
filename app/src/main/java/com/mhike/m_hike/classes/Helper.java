package com.mhike.m_hike.classes;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;

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

    public static String trimStr(TextInputLayout textField, boolean istrimed) {
        return textField.getEditText().getText().toString();

    }

    public static LocalDate getCurrentDate(){
        return LocalDate.now();

    }

    public static String[] getParkingArray(){
        return new String[]{"Yes", "No"};
    }
    public static String[] getDifficultyArray(){
        return  new String[]{"Easy", "Medium", "Hard"};
    }


}
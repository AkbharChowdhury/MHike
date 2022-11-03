package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mhike.m_hike.classes.DatePickerFragment;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.IDatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AddHikeActivity extends AppCompatActivity implements IDatePicker {

    private AutoCompleteTextView parkingAutoComplete;
    private AutoCompleteTextView difficultyAutoComplete;
    private AutoCompleteTextView txtHikeDate;

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        setTitle("Add Hike");

//        parkingAutoComplete = findViewById(R.id.autoCompleteParking);
//        parkingAutoComplete.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, Helper.getParkingArray()));
//
//        difficultyAutoComplete = findViewById(R.id.autoCompleteDifficulty);
//        difficultyAutoComplete.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, Helper.getDifficultyArray()));
//        txtHikeDate = findViewById(R.id.txtHikeDate);
//
//        txtHikeDate.setOnTouchListener((view, motionEvent) -> {
//
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                showDatePickerDialog();
//            }
//            return false;
//        });

    }



    public void showDatePickerDialog() {
        DialogFragment datePicker = new DatePickerFragment(true);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    private String formatDate(String date) {
        String[] dateSplit = date.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);
        LocalDate selectedDate = LocalDate.of(year, month, day);
        String fullDay = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(selectedDate);
        return fullDay;
    }


    @Override
    public void updateDate(LocalDate dob) {
        txtHikeDate.setText(formatDate(dob.toString()));

    }

}
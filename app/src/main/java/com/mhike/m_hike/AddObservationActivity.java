package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.DatePickerFragment;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Validation;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.interfaces.IDatePicker;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class AddObservationActivity extends AppCompatActivity implements IDatePicker {
    private DatabaseHelper db;
    private Context context;
    private Validation form;
    private final String CURRENT_DATE = Helper.getCurrentDate().toString();
    private String dbHikeDate = CURRENT_DATE;
    private String dbHikeTime;


    private AutoCompleteTextView txtHikeDate;
    private AutoCompleteTextView txtHikeName;

    private TextInputLayout txtObservation;
    private TextInputLayout txtComments;
    private AutoCompleteTextView txtHikeTime;
    int minute, hour;



    private void findTextFields() {
        txtHikeDate = findViewById(R.id.txtHikeDate);
        txtObservation = findViewById(R.id.txtObservation);
        txtComments = findViewById(R.id.txtComments);
        txtHikeTime = findViewById(R.id.txtHikeTime);
        txtHikeName = findViewById(R.id.txtHikeName);






    }

    public void popTimePicker()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                 hour = selectedHour;
                 minute = selectedMinute;
                 dbHikeTime = String.format(Locale.getDefault(), "%02d:%02d",hour, minute);
                 txtHikeTime.setText("");

            }
        };
        // link: https://github.com/codeWithCal/TimePickerAndroidStudio/blob/master/app/src/main/java/codewithcal/au/timerpickertutorial/MainActivity.java

         int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        context = getApplicationContext();
        findTextFields();

        db = DatabaseHelper.getInstance(context);
        form = new Validation(context);
        txtHikeDate.setText(Helper.formatDate(CURRENT_DATE));
        setDateAndTimeFields();

        setupAdapter();


    }

    private void setupAdapter() {
        String userID = String.valueOf(getUserID());
        txtHikeName.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item,  db.getHikeNameList(userID).toArray()));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setDateAndTimeFields() {
        txtHikeDate.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return false;
        });




        txtHikeTime.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                popTimePicker();
            }
            return false;
        });
    }
    @Override
    public void showDatePickerDialog() {
        DialogFragment datePicker = new DatePickerFragment(false, ActivityForm.ADD_OBSERVATION);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void updateDate(LocalDate selectedDate) {
        // for storing the date in the database
        dbHikeDate = selectedDate.toString();

        Helper.longToastMessage(context, dbHikeDate);
        // show user-friendly date formatted
        txtHikeDate.setText(Helper.formatDate(selectedDate.toString()));

    }

    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }
}
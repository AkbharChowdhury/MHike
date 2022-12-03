package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.utilities.DatePickerFragment;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.models.Observation;
import com.mhike.m_hike.utilities.Validation;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.interfaces.IDatePicker;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddObservationActivity extends AppCompatActivity implements IDatePicker {
    private final Activity CURRENT_ACTIVITY = AddObservationActivity.this;

    private DatabaseHelper db;
    private Context context;
    private Validation form;
    private final String CURRENT_DATE = Helper.getCurrentDate().toString();
    private String dbHikeDate = CURRENT_DATE;
    private String dbHikeTime;


    private AutoCompleteTextView txtObservationDate;
    private AutoCompleteTextView txtHikeName;

    private TextInputLayout txtObservation;
    private TextInputLayout txtComments;
    private AutoCompleteTextView txtHikeTime;
    int minute, hour;



    private void findTextFields() {
        txtObservationDate = findViewById(R.id.txtObservationDate);
        txtObservation = findViewById(R.id.txtObservation);
        txtComments = findViewById(R.id.txtComments);
        txtHikeTime = findViewById(R.id.txtHikeTime);
        txtHikeName = findViewById(R.id.txtHikeName);

    }

    public void popTimePicker()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
             hour = selectedHour;
             minute = selectedMinute;
             dbHikeTime = String.format(Locale.getDefault(), "%02d:%02d",hour, minute);
             txtHikeTime.setText(Helper.formatTime(dbHikeTime));

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
        setTitle("Add Observation");

        findTextFields();

        db = DatabaseHelper.getInstance(context);
        form = new Validation(context);

        txtObservationDate.setText(Helper.formatDate(CURRENT_DATE));
        setDateAndTimeFields();
        setupAdapter();
        Button btnAddObservation = findViewById(R.id.btn_add_obj);
        btnAddObservation.setOnClickListener(view -> addObservations());


    }

    private void addObservations() {
        Observation observation = new Observation(txtHikeName, txtObservation, txtComments, txtObservationDate, txtHikeTime);
        int hikeID = db.getHikeIDByName(txtHikeName.getText().toString());

        observation.setHikeID(hikeID);
        observation.setObservation(Helper.trimStr(txtObservation));
        observation.setComments(Helper.trimStr(txtComments));
        observation.setObservationDate(dbHikeDate);
        observation.setObservationTime(dbHikeTime);

        List<Observation> observationList = new ArrayList<>();
        observationList.add(observation);

        if(form.validateObservationForm(observation)){
            db.addObservation(observationList);
                Helper.longToastMessage(context, "Observation added");
        }
    }



    private void setupAdapter() {
        String userID = String.valueOf(getUserID());


        txtHikeName.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item,
                db.getUserHikes(userID)
                ));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setDateAndTimeFields() {
        txtObservationDate.setOnTouchListener((view, motionEvent) -> {

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


        DialogFragment datePicker = new DatePickerFragment(false, ActivityForm.ADD_OBSERVATION,  9309403);

        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void updateDate(LocalDate selectedDate) {
        // for storing the date in the database
        dbHikeDate = selectedDate.toString();

        // show user-friendly date formatted
        txtObservationDate.setText(Helper.formatDate(selectedDate.toString()));

    }

    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }
}
package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.DatePickerFragment;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Hike;
import com.mhike.m_hike.classes.IDatePicker;
import com.mhike.m_hike.classes.User;
import com.mhike.m_hike.classes.Validation;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.ParkingTable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AddHikeActivity extends AppCompatActivity implements IDatePicker {
    private DatabaseHelper db;
    private Context context;
    private Validation form;

    private AutoCompleteTextView txtHikeDate;
    private TextInputLayout txtHikeName;
    private TextInputLayout txtDescription;
    private TextInputLayout txtLocation;
    private TextInputLayout txtDistance;
    private TextInputLayout txtDuration;
    private AutoCompleteTextView txtParking;
    private TextInputLayout txtElevationGain;
    private TextInputLayout txtHigh;
    private AutoCompleteTextView txtDifficulty;
    

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        form = new Validation(context);

        setTitle("Add Hike");

        findTextFields();

        setupAdapter();

        txtDifficulty.isEnabled();
        txtDifficulty.setText(txtDifficulty.getAdapter().getItem(1).toString(), false);


//        txtDifficulty.setText("Easy");




        txtHikeDate.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return false;
        });

        Button btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> handleHike());

    }

    private void findTextFields() {

        txtHikeDate = findViewById(R.id.txtHikeDate);
        txtHikeName = findViewById(R.id.txtHikeName);
        txtDescription = findViewById(R.id.txtDescription);
        txtLocation = findViewById(R.id.txtLocation);
        txtDistance = findViewById(R.id.txtDistance);
        txtDuration = findViewById(R.id.txtDuration);
        txtParking = findViewById(R.id.txtParking);
        txtElevationGain = findViewById(R.id.txtElevationGain);
        txtHigh = findViewById(R.id.txtHigh);
        txtDifficulty = findViewById(R.id.txtDifficulty);

    }

    private void setupAdapter() {
        txtParking.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, db.populateDropdown(ParkingTable.TABLE_NAME)));
        txtDifficulty.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, db.populateDropdown(DifficultyTable.TABLE_NAME)));

    }

    private void handleHike() {
        Hike hike = new Hike(txtHikeDate,  txtHikeName, txtDescription,  txtLocation,  txtDistance,  txtDuration,  txtParking, txtElevationGain,  txtHigh, txtDifficulty);

        if (form.validateHikeForm(hike)){
            Helper.longToastMessage(context,"Done");
        }



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
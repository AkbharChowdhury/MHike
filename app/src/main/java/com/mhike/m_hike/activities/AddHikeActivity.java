package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.models.User;
import com.mhike.m_hike.utilities.DatePickerFragment;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.interfaces.IDatePicker;
import com.mhike.m_hike.utilities.Validation;
import com.mhike.m_hike.classes.interfaces.IDifficulty;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.ParkingTable;

import java.time.LocalDate;
import java.util.List;

public class AddHikeActivity extends AppCompatActivity implements IDatePicker, IDifficulty {
    private final Activity CURRENT_ACTIVITY = AddHikeActivity.this;
    private DatabaseHelper db;
    private Context context;

    private Validation form;
    private final String CURRENT_DATE = Helper.getCurrentDate().toString();
    private String dbHikeDate = CURRENT_DATE;

    private AutoCompleteTextView txtHikeDate;
    private TextInputLayout txtHikeName;
    private TextInputLayout txtDescription;
    private TextInputLayout txtLocation;
    private TextInputLayout txtDistance;
    private TextInputLayout txtDuration;
    private AutoCompleteTextView txtParking;
    private TextInputLayout txtElevationGain;
    private TextInputLayout txtHigh;
    private TextView lblDifficulty;
    private List<String> difficultyList;

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());
        db = DatabaseHelper.getInstance(context);
        form = new Validation(context, db);
        difficultyList = db.populateDropdown(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE);
        checkIsUserLoggedIn();

        setTitle(getString(R.string.add_hike_title));

        findTextFields();
        setupAdapter();
        setTxtHikeDate();
        Button btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> handleHike());

        txtHikeDate.setText(Helper.formatDate(CURRENT_DATE));
        txtElevationGain.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> showDifficultyLevel());

    }


    private void checkIsUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        int userID = preferences.getInt(AccountPreferences.USERID, 0);
        if (userID == 0) {
            Helper.SetRedirectMessage(CURRENT_ACTIVITY, LoginActivity.class, getString(R.string.login_required));

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTxtHikeDate() {
        txtHikeDate.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return false;
        });
    }


    private Hike getHikeDetails() {


        String hikeDate = dbHikeDate;
        String hikeName = Helper.trimStr(txtHikeName);
        String description = Helper.trimStr(txtDescription);
        String location = Helper.trimStr(txtLocation);
        double distance = Double.parseDouble(Helper.trimStr(txtDistance));
        double duration = Double.parseDouble(Helper.trimStr(txtDuration));
        int parkingID = db.getColumnID(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_TYPE, ParkingTable.COLUMN_ID, txtParking.getText().toString());
        double elevationGain = Double.parseDouble(Helper.trimStr(txtElevationGain));
        double high = Double.parseDouble(Helper.trimStr(txtHigh));
        int difficultyID = db.getColumnID(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE, DifficultyTable.COLUMN_ID, lblDifficulty.getText().toString());


        Hike hikeDetails = new Hike();
        hikeDetails.setHikeDate(hikeDate);
        hikeDetails.setHikeName(hikeName);
        hikeDetails.setDescription(description);
        hikeDetails.setLocation(location);
        hikeDetails.setDistance(distance);
        hikeDetails.setDuration(duration);
        hikeDetails.setParkingID(parkingID);
        hikeDetails.setElevationGain(elevationGain);
        hikeDetails.setHigh(high);
        hikeDetails.setDifficultyID(difficultyID);
        return hikeDetails;

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
        lblDifficulty = findViewById(R.id.lbl_difficulty);

    }


    private void setupAdapter() {
        txtParking.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, db.populateDropdown(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_TYPE)));

    }

    private void handleHike() {
        Hike hike = new Hike(txtHikeDate, txtHikeName, txtDescription, txtLocation, txtDistance, txtDuration, txtParking, txtElevationGain, txtHigh, lblDifficulty);
        form.disableFormValidation = false;// show error messages

        if (form.validateHikeForm(hike)) {

            int userID = User.getUserID(getApplicationContext());

            if (db.addHike(getHikeDetails(), userID)) {
                Helper.SetRedirectMessage(CURRENT_ACTIVITY, HikeActivity.class, getString(R.string.hike_added_success));
                return;
            }

            Helper.longToastMessage(context, getString(R.string.hike_added_error));
        }

    }

    @Override
    public void showDatePickerDialog() {
        DialogFragment datePicker = new DatePickerFragment(true, ActivityForm.ADD_HIKE);
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

    @Override
    public void showDifficultyLevel() {

        form.isValidElevation(txtElevationGain);
        // calculate the difficulty based on the elevation and high

        form.disableFormValidation = true; // hide error messages
        if (form.isValidElevation(txtElevationGain) && form.isValidDistance(txtDistance)) {
            double elevationGain = Double.parseDouble(Helper.trimStr(txtElevationGain));
            double distance = Double.parseDouble(Helper.trimStr(txtDistance));
            int difficultyLevel = Helper.getDifficultyLevel(distance, elevationGain);
            String difficultyName = difficultyList.get(difficultyLevel);
            lblDifficulty.setTextColor(Helper.getDifficultyColour(difficultyLevel, context));
            lblDifficulty.setText(difficultyName);
        }


    }


}
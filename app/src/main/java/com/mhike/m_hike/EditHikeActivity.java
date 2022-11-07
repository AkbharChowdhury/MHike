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
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.DatePickerFragment;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Hike;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.interfaces.IDatePicker;
import com.mhike.m_hike.classes.Validation;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ParkingTable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class EditHikeActivity extends AppCompatActivity implements IDatePicker {
    int hikeID;

    private DatabaseHelper db;
    private Context context;
    private Validation form;
    private String dbHikeDate;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        setTitle(getString(R.string.edit_hike_title));
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        form = new Validation(context);


        findTextFields();
        setupAdapter();
        setTxtHikeDate();
        getIntentAndSetData();
        Button btnUpdateHike = findViewById(R.id.btn_edit_hike);
        btnUpdateHike.setOnClickListener(view -> updateHike());

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
        int difficultyID = db.getColumnID(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE, DifficultyTable.COLUMN_ID, txtDifficulty.getText().toString());


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

    private void updateHike() {
        Hike hike = new Hike(txtHikeDate, txtHikeName, txtDescription, txtLocation, txtDistance, txtDuration, txtParking, txtElevationGain, txtHigh, txtDifficulty);

        if (form.validateHikeForm(hike)) {

            SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
            int userID = preferences.getInt(AccountPreferences.USERID, 0);
            
            if (db.updateHike(getHikeDetails(), getIntent().getStringExtra("hikeID"), userID)) {
                Helper.toastMessage(context, "Hike updated successfully");
                return;
            }
            Helper.toastMessage(context, "could not update hike");
        }
    }


    private void getIntentAndSetData() {

        if (getIntent().hasExtra("hikeID")) {
            String hikeID = getIntent().getStringExtra("hikeID");
            List<Hike> hikeList = db.getSelectedHike(hikeID);

            if (hikeList.size() == 0){
                Helper.longToastMessage(context, "No hikes found");
                return;
            }

            for (Hike hike : hikeList){

                txtHikeDate.setText(formatDate(hike.getHikeDate()));

                dbHikeDate = hike.getHikeDate();
                txtHikeName.getEditText().setText(hike.getHikeName());
                txtDescription.getEditText().setText(hike.getDescription());
                txtLocation.getEditText().setText(hike.getLocation());
                txtDistance.getEditText().setText(String.valueOf(hike.getDistance()));
                txtDuration.getEditText().setText(String.valueOf(hike.getDuration()));
                String parkingStr = db.getColumnName(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_ID, String.valueOf(hike.getParkingID()), ParkingTable.COLUMN_TYPE);
                txtParking.setText(parkingStr);
                txtElevationGain.getEditText().setText(String.valueOf(hike.getElevationGain()));
                txtHigh.getEditText().setText(String.valueOf(hike.getHigh()));
                String difficultyStr = db.getColumnName(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_ID, String.valueOf(hike.getDifficultyID()), DifficultyTable.COLUMN_TYPE);
                txtDifficulty.setText(difficultyStr);


            }



        }
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

    @SuppressLint("ClickableViewAccessibility")
    private void setTxtHikeDate() {
        txtHikeDate.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return false;
        });
    }

    public void showDatePickerDialog() {
        DialogFragment datePicker = new DatePickerFragment(false, ActivityForm.EDIT_HIKE);
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
    public void updateDate(LocalDate selectedDate) {
        // for storing the date in the database
        dbHikeDate = selectedDate.toString();

//        Helper.longToastMessage(context, dbHikeDate);
        // show user-friendly date formatted
        txtHikeDate.setText(formatDate(selectedDate.toString()));

    }

}

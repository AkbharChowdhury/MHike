package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ParkingTable;

import java.time.LocalDate;
import java.util.List;

public class EditHikeActivity extends AppCompatActivity implements IDatePicker, IDifficulty {
    private final Activity CURRENT_ACTIVITY = EditHikeActivity.this;

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
    private TextView lblDifficulty;
    private List<String> difficultyList;
    private List<String> parkingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        setTitle(getString(R.string.edit_hike_title));
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());

        db = DatabaseHelper.getInstance(context);
        form = new Validation(context, db);
        form.setValidateHikeExists(false);
        difficultyList = db.populateDropdown(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE);
        parkingList = db.populateDropdown(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_TYPE);


        findTextFields();
        setupAdapter();
        setTxtHikeDate();
        getIntentAndSetData();
        Button btnUpdateHike = findViewById(R.id.btn_edit_hike);
        btnUpdateHike.setOnClickListener(view -> updateHike());
        txtElevationGain.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> showDifficultyLevel());
        Button btnDeleteHike = findViewById(R.id.btn_delete_hike);
        btnDeleteHike.setOnClickListener(view -> deleteHike(getIntent().getStringExtra("hikeID")));


    }

    private void deleteHike(String hikeID) {

        new AlertDialog.Builder(EditHikeActivity.this)
                .setMessage(getString(R.string.hike_subtitle_msg)).setCancelable(false)
                .setTitle(getString(R.string.hike_title_confirm))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    if (db.deleteRecord(HikeTable.TABLE_NAME, HikeTable.COLUMN_ID, hikeID)) {
                        Intent intent = new Intent(context, HikeActivity.class);
                        intent.putExtra("message", true);
                        startActivity(intent);

                    }


                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel()).create().show();

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

    private void updateHike() {
        Hike hike = new Hike(txtHikeDate, txtHikeName,
                txtDescription,
                txtLocation,
                txtDistance,
                txtDuration,
                txtParking,
                txtElevationGain,
                txtHigh,
                lblDifficulty);

        if (form.validateHikeForm(hike)) {

//            SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
//            int userID = preferences.getInt(AccountPreferences.USERID, 0);
            int userID = User.getUserID(getApplicationContext());

            if (db.updateHike(getHikeDetails(), getIntent().getStringExtra("hikeID"), userID)) {
                Helper.SetRedirectMessage(CURRENT_ACTIVITY, HikeActivity.class, getString(R.string.hike_updated_success));
                return;
            }
            Helper.toastMessage(context, getString(R.string.hike_update_error));
        }
    }




    private void getIntentAndSetData() {

        if (getIntent().hasExtra("hikeID")) {
            String hikeID = getIntent().getStringExtra("hikeID");
            List<Hike> hikeList = db.getSelectedHike(hikeID);

            if (hikeList.size() == 0) {
                Helper.longToastMessage(context, "No hikes found");
                return;
            }

            for (Hike hike : hikeList) {

                txtHikeDate.setText(Helper.formatDate(hike.getHikeDate()));

                dbHikeDate = hike.getHikeDate();
                txtHikeName.getEditText().setText(hike.getHikeName());
                txtDescription.getEditText().setText(hike.getDescription());
                txtLocation.getEditText().setText(hike.getLocation());
                txtDistance.getEditText().setText(String.valueOf(hike.getDistance()));
                txtDuration.getEditText().setText(String.valueOf(hike.getDuration()));
                String parkingStr = db.getColumnName(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_ID, String.valueOf(hike.getParkingID()), ParkingTable.COLUMN_TYPE);
                txtParking.setText(txtParking.getAdapter().getItem(hike.getParkingID() == 1? 0: 1).toString(), false);

//                txtParking.setText(parkingStr);
//                txtParking.set
                // change filtering for the adapter so all items can be visible in drop-down menu

//                txtParking.setText(txtParking.getAdapter().getItem().toString());
//                txtParking.setListSelection(1);

                txtElevationGain.getEditText().setText(String.valueOf(hike.getElevationGain()));
                txtHigh.getEditText().setText(String.valueOf(hike.getHigh()));
                String difficultyStr = db.getColumnName(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_ID, String.valueOf(hike.getDifficultyID()), DifficultyTable.COLUMN_TYPE);
                lblDifficulty.setText(difficultyStr);


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
        lblDifficulty = findViewById(R.id.lbl_difficulty);

    }

    private void setupAdapter() {
        txtParking.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, db.populateDropdown(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_TYPE)));

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
        DialogFragment datePicker = new DatePickerFragment(true, ActivityForm.EDIT_HIKE);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void updateDate(LocalDate selectedDate) {
        // for storing the date in the database
        dbHikeDate = selectedDate.toString();
        // show user-friendly date formatted
        txtHikeDate.setText(Helper.formatDate(selectedDate.toString()));

    }

    @Override
    public void showDifficultyLevel() {
        // calculate the difficulty based on the elevation and high
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

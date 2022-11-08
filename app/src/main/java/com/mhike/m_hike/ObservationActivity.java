package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.HikeAdapter;
import com.mhike.m_hike.classes.ObservationAdapter;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ObservationTable;

import java.util.ArrayList;

public class ObservationActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private ArrayList<String> observationID;
    private ArrayList<String> observation;
    private ArrayList<String> observationDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        setTitle(getString(R.string.observation_title));
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);

        observationID = new ArrayList<>();
        observation = new ArrayList<>();
        observationDateTime = new ArrayList<>();


        RecyclerView recyclerView = findViewById(R.id.observation_recyclerview);
        ObservationAdapter adapter = new ObservationAdapter(
                ObservationActivity.this,
                context,
                observationID,
                observation,
                observationDateTime
                );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showObservationList();






        FloatingActionButton btnAddObservation = findViewById(R.id.btn_add_observation);
        btnAddObservation.setOnClickListener(view -> startActivity(new Intent(context, AddObservationActivity.class)));
    }

    @SuppressLint("Range")
    private void showObservationList() {
        try (Cursor cursor = db.getObservationList(String.valueOf(getUserID()))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, "No observations found");
                return;
            }

            while (cursor.moveToNext()) {
                observationID.add(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_ID)));
                observation.add(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_OBSERVATION)));
                observationDateTime.add(cursor.getString(cursor.getColumnIndex(Helper.formatDate(ObservationTable.COLUMN_DATE))));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }
}
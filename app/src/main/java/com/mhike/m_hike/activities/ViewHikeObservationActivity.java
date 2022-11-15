package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.adapters.ObservationAdapter;
import com.mhike.m_hike.classes.tables.ObservationTable;

import java.util.ArrayList;

public class ViewHikeObservationActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private ArrayList<String> observationID;
    private ArrayList<String> observation;
    private ArrayList<String> observationDate;
    private ArrayList<String> observationTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);

        observationID = new ArrayList<>();
        observation = new ArrayList<>();
        observationDate = new ArrayList<>();
        observationTime = new ArrayList<>();



        RecyclerView recyclerView = findViewById(R.id.observation_recyclerview);
        ObservationAdapter adapter = new ObservationAdapter(
                ViewHikeObservationActivity.this,
                context,
                observationID,
                observation,
                observationDate,
                observationTime
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showObservationList();

    }

    @SuppressLint("Range")
    private void showObservationList() {

        if (!getIntent().hasExtra("hikeID")) {
            Helper.longToastMessage(context, "error hike id not found");
            return;
        }

        String hikeID = getIntent().getStringExtra("hikeID");
        String userID = String.valueOf(getUserID());

        setTitle("Observation details for " + db.getHikeNameListByID(hikeID));

        try (Cursor cursor = db.getObservationList(userID, hikeID)) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, "No observations found");
                return;
            }

            while (cursor.moveToNext()) {
                observationID.add(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_ID)));
                observation.add(cursor.getString(cursor.getColumnIndex(ObservationTable.OBSERVATION_TITLE)));
                observationDate.add(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_DATE)));
                observationTime.add(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_TIME)));

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
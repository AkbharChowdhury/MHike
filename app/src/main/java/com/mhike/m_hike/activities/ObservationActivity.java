package com.mhike.m_hike.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.adapters.HikeAdapter;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.tables.HikeTable;

import java.util.ArrayList;
import java.util.List;

public class ObservationActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private final Activity CURRENT_ACTIVITY = ObservationActivity.this;
    private ArrayList<Hike> hikeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        setTitle(getString(R.string.objHikes));
        context = getApplicationContext();
        checkMessage();
        FloatingActionButton btnObservation = findViewById(R.id.btn_add_observation);
        btnObservation.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, AddObservationActivity.class));
        db = DatabaseHelper.getInstance(context);
        hikeList = (ArrayList<Hike>) showHikeObservationList();

        RecyclerView recyclerView = findViewById(R.id.observation_recyclerview);

        HikeAdapter adapter = new HikeAdapter(
                hikeList,
                ObservationActivity.this,
                context,
                ActivityForm.OBSERVATION_RECYCLER
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void checkMessage() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("message")) {
            Helper.longToastMessage(context, getString(R.string.hike_deleted_confirm));
            extras.remove("message");

        }
    }


    @SuppressLint("Range")
    private List<Hike> showHikeObservationList() {
        List<Hike> list = new ArrayList<>();

        try (Cursor cursor = db.getHikeListObservation(String.valueOf(getUserID()))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return list;
            }

            while (cursor.moveToNext()) {

                list.add(new Hike(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID))),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE))

                ));

            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;


    }

    // refresh the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private int getUserID() {
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }

}
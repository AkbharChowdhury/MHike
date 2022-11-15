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
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.adapters.HikeAdapter;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.tables.HikeTable;

import java.util.ArrayList;

public class HikeActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private final Activity CURRENT_ACTIVITY = HikeActivity.this;

    private ArrayList<String> hikeName;
    private ArrayList<String> hikeDescription;
    private ArrayList<String> hikeDate;
    private ArrayList<String> hikeID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike);
        checkMessage();
        setTitle(getString(R.string.nav_hikes));
        context = getApplicationContext();
        FloatingActionButton btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, AddHikeActivity.class));

        db = DatabaseHelper.getInstance(context);

        hikeID = new ArrayList<>();
        hikeName = new ArrayList<>();
        hikeDescription = new ArrayList<>();
        hikeDate = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.hike_recyclerview);
        HikeAdapter adapter = new HikeAdapter(
                HikeActivity.this,
                context,
                ActivityForm.HIKE_RECYCLER,
                hikeID,
                hikeName,
                hikeDescription,
                hikeDate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showHikeList();
    }


    private void checkMessage() {
        Bundle extras = getIntent().getExtras();
        String key = Helper.TOAST_MESSAGE;
        if (extras != null && extras.getBoolean(key)) {
            Helper.longToastMessage(context, extras.getString(key));
            extras.clear();

        }

    }




    @SuppressLint("Range")
    private void showHikeList() {

        try (Cursor cursor = db.getHikeList(String.valueOf(getUserID()))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return;
            }

            while (cursor.moveToNext()) {
                hikeID.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID)));
                hikeName.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)));
                hikeDescription.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)));
                hikeDate.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE)));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



    // refresh the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }

}
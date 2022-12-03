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

public class HikeActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = HikeActivity.this;
    private Context context;
    private DatabaseHelper db;

    private ArrayList<Hike> hikeList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike);
        setTitle(getString(R.string.nav_hikes));
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());

        FloatingActionButton btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, AddHikeActivity.class));

        db = DatabaseHelper.getInstance(context);

        hikeList = (ArrayList<Hike>) getHikeListData();


        RecyclerView recyclerView = findViewById(R.id.hike_recyclerview);
        HikeAdapter adapter = new HikeAdapter(
                hikeList,
                HikeActivity.this,
                context,
                ActivityForm.HIKE_RECYCLER
                );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }





    @SuppressLint("Range")
    private List<Hike> getHikeListData() {
        List<Hike> list = new ArrayList<>();


        try (Cursor cursor = db.getHikeList(String.valueOf(getUserID()))) {

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
        if(requestCode == 1){
            recreate();
        }
    }

    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }

}
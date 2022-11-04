package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Hike;

import java.util.ArrayList;

public class EditHikeActivity extends AppCompatActivity {
    int hikeID;
    DatabaseHelper db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        getIntentAndSetData();
    }

    private void getIntentAndSetData() {

        if (getIntent().hasExtra("hikeID")) {
            String hikeID = getIntent().getStringExtra("hikeID");
            Helper.longToastMessage(context, hikeID);

        }
    }

}

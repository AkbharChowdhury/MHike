package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.utilities.Helper;

public class UploadHikeActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = UploadHikeActivity.this;
    private DatabaseHelper db;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hike);
        setTitle(getString(R.string.upload_hike));
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());
        db = DatabaseHelper.getInstance(context);
    }
}
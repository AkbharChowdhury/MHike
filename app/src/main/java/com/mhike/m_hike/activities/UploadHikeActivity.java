package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mhike.m_hike.R;

public class UploadHikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hike);
        setTitle(getString(R.string.upload_hike));
    }
}
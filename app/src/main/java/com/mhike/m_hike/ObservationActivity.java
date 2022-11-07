package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ObservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        setTitle(getString(R.string.observation_title));
    }
}
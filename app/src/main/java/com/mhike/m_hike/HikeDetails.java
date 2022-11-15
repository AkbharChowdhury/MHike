package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HikeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);
        setTitle("Hike details");
    }
}
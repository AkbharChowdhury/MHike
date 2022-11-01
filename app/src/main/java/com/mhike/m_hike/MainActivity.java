package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mhike.m_hike.classes.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

    }
}
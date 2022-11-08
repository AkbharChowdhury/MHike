package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ObservationActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        setTitle(getString(R.string.observation_title));
        context = getApplicationContext();

        FloatingActionButton btnAddObservation = findViewById(R.id.btn_add_observation);
        btnAddObservation.setOnClickListener(view -> startActivity(new Intent(context, AddObservationActivity.class)));
    }
}
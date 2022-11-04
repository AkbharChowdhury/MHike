package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mhike.m_hike.classes.IDatePicker;

import java.time.LocalDate;

public class AddObservationActivity extends AppCompatActivity implements IDatePicker {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
    }

    @Override
    public void updateDate(LocalDate dob) {

    }
}
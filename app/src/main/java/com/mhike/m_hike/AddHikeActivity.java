package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mhike.m_hike.classes.Helper;

public class AddHikeActivity extends AppCompatActivity {

    private AutoCompleteTextView parkingAutoComplete;
    private AutoCompleteTextView difficultyAutoComplete;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        setTitle("Add Hike");

        parkingAutoComplete = findViewById(R.id.autoCompleteParking);
        parkingAutoComplete.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, Helper.getParkingArray()));

        difficultyAutoComplete = findViewById(R.id.autoCompleteDifficulty);
        difficultyAutoComplete.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, Helper.getDifficultyArray()));




    }
}
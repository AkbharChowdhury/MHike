package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.ParkingTable;

public class HikesActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hikes);
        setTitle(getString(R.string.nav_hikes));
        context = getApplicationContext();
        FloatingActionButton btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddHikeActivity.class)));
        DatabaseHelper db = DatabaseHelper.getInstance(context);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_logout){
            AccountPreferences.logout(context);
        }
        if(item.getItemId() == R.id.nav_hikes){
            startActivity(new Intent(context, HikesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

}
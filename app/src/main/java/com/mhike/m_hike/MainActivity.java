package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.tables.ParkingTable;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        CheckIsUserLoggedIn();
        setTitle(getString(R.string.nav_home));
        startActivity(new Intent(this, HikeActivity.class));



    }

    private void CheckIsUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        int userID = preferences.getInt(AccountPreferences.USERID, 0);
        if (userID == 0) {
            // redirect to login page if user is not logged in
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(AccountPreferences.USERID, 0);
                editor.apply();
                // redirect to login page
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.nav_hike:
                Helper.longToastMessage(context, "ddddd");
                startActivity(new Intent(context, HikeActivity.class));
                break;

            case R.id.nav_add_observation:
//                startActivity(new Intent(context, AddObservationActivity.class));
                break;
            default:
                super.onOptionsItemSelected(item);
        }



        return super.onOptionsItemSelected(item);
    }
}
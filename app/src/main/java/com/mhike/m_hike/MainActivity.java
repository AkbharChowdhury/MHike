package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;

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
}
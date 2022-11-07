package com.mhike.m_hike.classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.mhike.m_hike.HikeActivity;
import com.mhike.m_hike.LoginActivity;
import com.mhike.m_hike.R;

public class CustomMenu  extends AppCompatActivity {
    private Context context;
    Activity activity;


    public CustomMenu(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        activity.getMenuInflater().inflate(R.menu.toolbar_menu, menu);

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

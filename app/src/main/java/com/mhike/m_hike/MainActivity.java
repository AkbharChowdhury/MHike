package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.CustomMenu;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Hike;
import com.mhike.m_hike.classes.User;
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
        TextView user_dashboard = findViewById(R.id.lbl_user_dashboard);

        User user = db.getUserFirstAndLastName(String.valueOf(getUserID()));
//        user_dashboard.setText(userDetails.getFirstname() + " " + userDetails.getLastname());
        user_dashboard.setText(getString(R.string.get_user_first_last_name));


        user_dashboard.setText(
                String.format(getString(R.string.get_user_first_last_name),
                        user.getFirstname(), user.getLastname()
                ));
//        user_dashboard.setText(db.getUserFirstAndLastName("1");
        buttonCard();


    }

    private void buttonCard() {
        CardView hikeCard = findViewById(R.id.card_hike);
        CardView searchCard = findViewById(R.id.card_search);
        CardView sightsCard = findViewById(R.id.card_sights);
        CardView uploadCard = findViewById(R.id.card_upload_hike);
        CardView logoutCard = findViewById(R.id.card_logout);

        hikeCard.setOnClickListener(view -> startActivity(new Intent(context, HikeActivity.class)));
        sightsCard.setOnClickListener(view -> startActivity(new Intent(context, ObservationActivity.class)));
        logoutCard.setOnClickListener(view -> logout());


    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AccountPreferences.USERID, 0);
        editor.apply();
        // redirect to login page
        startActivity(new Intent(context, LoginActivity.class));
    }


    private void CheckIsUserLoggedIn() {
        int userID = getUserID();
        if (userID == 0) {
            // redirect to login page if user is not logged in
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void hikePage(View view) {
        startActivity(new Intent(context, HikeActivity.class));

    }
    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }


}
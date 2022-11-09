package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.User;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private final Activity CURRENT_ACTIVITY = MainActivity.this;
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
        user_dashboard.setText(getString(R.string.get_user_first_last_name));


        user_dashboard.setText(
                String.format(getString(R.string.get_user_first_last_name),
                        Helper.capitalise(user.getFirstname()), Helper.capitalise(user.getLastname())
                ));


        buttonCards();





    }

    private void buttonCards() {
        CardView hikeCard = findViewById(R.id.card_hike);
        CardView searchCard = findViewById(R.id.card_search);
        CardView sightsCard = findViewById(R.id.card_sights);
        CardView uploadCard = findViewById(R.id.card_upload_hike);
        CardView logoutCard = findViewById(R.id.card_logout);

        hikeCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, HikeActivity.class));
        sightsCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, ObservationActivity.class));

        logoutCard.setOnClickListener(view -> logout());


    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AccountPreferences.USERID, 0);
        editor.apply();
        // redirect to login page
        Helper.goToPage(CURRENT_ACTIVITY, LoginActivity.class);
    }


    private void CheckIsUserLoggedIn() {
        int userID = getUserID();
        if (userID == 0) {
            Helper.goToPage(CURRENT_ACTIVITY, LoginActivity.class);

        }
    }


    private int getUserID(){
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }


}
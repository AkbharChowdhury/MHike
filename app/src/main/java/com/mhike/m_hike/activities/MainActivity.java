package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.models.User;

public class MainActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = MainActivity.this;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        CheckIsUserLoggedIn();


        DatabaseHelper db = DatabaseHelper.getInstance(context);
        setTitle(getString(R.string.nav_home));
        TextView user_dashboard = findViewById(R.id.lbl_user_dashboard);

        User user = db.getUserFirstAndLastName(String.valueOf(User.getUserID(context)));
        user_dashboard.setText(getString(R.string.full_name));


        user_dashboard.setText(
                String.format(getString(R.string.full_name),
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

//        hikeCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, HikeActivity.class));
        hikeCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, HikeActivity.class));

        sightsCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, ObservationActivity.class));
        searchCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, SearchHikeActivity.class));
        uploadCard.setOnClickListener(view -> Helper.goToPage(CURRENT_ACTIVITY, UploadHikeActivity.class));
        logoutCard.setOnClickListener(view -> logout());


    }

    private void logout() {
        User.logout(getApplicationContext());
        Helper.goToPage(CURRENT_ACTIVITY, LoginActivity.class);
    }


    private void CheckIsUserLoggedIn() {
        int userID = User.getUserID(getApplicationContext());
        if (userID == 0) {
            Helper.goToPage(CURRENT_ACTIVITY, LoginActivity.class);

        }
    }

}
package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.ParkingTable;
import com.mhike.m_hike.utilities.Helper;

import java.util.List;

public class HikeDetailsActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = HikeDetailsActivity.this;

    private DatabaseHelper db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);
        setTitle(getString(R.string.hike_details));
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        Helper.getIntentMessage(context, getIntent().getExtras());

        getIntentAndSetData();
    }

    private void getIntentAndSetData() {

        if (getIntent().hasExtra("hikeID")) {
            String hikeID = getIntent().getStringExtra("hikeID");
            List<Hike> hikeList = db.getSelectedHike(hikeID);

            if (hikeList.size() == 0) {
                Helper.longToastMessage(context, "No hikes found");
                return;
            }

            TextView hikeName = findViewById(R.id.hikeNameLbl);
            TextView description = findViewById(R.id.hikeDescriptionLbl);
            TextView date = findViewById(R.id.lblHikeDateView);
            TextView location = findViewById(R.id.locationLbl);
            TextView tvDistance = findViewById(R.id.distance);

            TextView duration = findViewById(R.id.duration);
            TextView parking = findViewById(R.id.parking);
            TextView elevationGain = findViewById(R.id.elevationGain);
            TextView difficulty = findViewById(R.id.difficulty);
            List<String> difficultyList = db.populateDropdown(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE);

            for (Hike hike : hikeList) {
                hikeName.setText(hike.getHikeName());
                description.setText(hike.getDescription());
                date.setText(Helper.formatDate(hike.getHikeDate()));
                location.setText(hike.getLocation());
                tvDistance.setText(String.valueOf(hike.getDistance()));
                duration.setText(String.valueOf(hike.getDuration()));
                String parkingStr = db.getColumnName(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_ID, String.valueOf(hike.getParkingID()), ParkingTable.COLUMN_TYPE);
                parking.setText(parkingStr);
                elevationGain.setText(String.valueOf(hike.getElevationGain()));



                String difficultyStr = db.getColumnName(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_ID, String.valueOf(hike.getDifficultyID()), DifficultyTable.COLUMN_TYPE);
//                difficulty.setText(difficultyStr);


                double distance = hike.getDistance();
                int difficultyLevel = Helper.getDifficultyLevel(distance, hike.getElevationGain());
                String difficultyName = difficultyList.get(difficultyLevel);
                difficulty.setTextColor(Helper.getDifficultyColour(difficultyLevel, context));
                difficulty.setText(difficultyName);

            }


        }
    }
}
package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.HikeJson;
import com.mhike.m_hike.classes.JsonThread;
import com.mhike.m_hike.classes.models.User;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ParkingTable;
import com.mhike.m_hike.utilities.Helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UploadHikeActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private WebView browser;

    // used for by the Json Thread class
    public WebView getBrowser() {
        return browser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hike);
        setTitle(getString(R.string.upload_hike));
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());
        db = DatabaseHelper.getInstance(context);
        browser = findViewById(R.id.browser);
        Log.d("HikeJsonData", getJsonData());
        uploadUserHikeDetails();

    }

    private String getJsonData() {

        try {
            List<HikeJson> detailList = getSelectedUserHikeData();
            User user = new User(detailList, User.getUserID(getApplicationContext()));
            return user.getJsonHike();
        } catch (Exception ex) {
            Log.d("HikeJSONUploadError", "there was an error converting json data" + ex.getMessage());
            Helper.longToastMessage(context, ex.getMessage());

        }
        return null;
    }

    private void uploadUserHikeDetails() {
        try {
            URL pageURL = new URL(getString(R.string.web_service_url));
            JsonThread.trustAllHosts();
            HttpURLConnection con = (HttpURLConnection) pageURL.openConnection();
            String jsonString = getJsonData();
            JsonThread myTask = new JsonThread(this, con, jsonString);
            Thread t1 = new Thread(myTask, "JSON Thread");
            t1.start();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @SuppressLint("Range")
    private List<HikeJson> getSelectedUserHikeData() {
        List<HikeJson> list = new ArrayList<>();
        int userID = User.getUserID(getApplicationContext());

        try (Cursor cursor = db.getHikeList(String.valueOf(userID))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes_web_service));
                return list;
            }

            while (cursor.moveToNext()) {
                int difficultyID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DIFFICULTY_ID)));
                int parkingID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_PARKING_ID)));

                String difficulty = db.getColumnName(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_ID, String.valueOf(difficultyID), DifficultyTable.COLUMN_TYPE);
                String parking = db.getColumnName(ParkingTable.TABLE_NAME, ParkingTable.COLUMN_ID, String.valueOf(parkingID), ParkingTable.COLUMN_TYPE);


                list.add(new HikeJson(
                        Helper.formatDate(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE))),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_LOCATION)),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DISTANCE))),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DURATION))),
                        parking,
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ELEVATION_GAIN))),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIGH))),
                        difficulty


                ));

            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


}
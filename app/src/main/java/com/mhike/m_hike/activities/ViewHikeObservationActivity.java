package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.models.Observation;
import com.mhike.m_hike.utilities.Helper;
import com.mhike.m_hike.classes.adapters.ObservationAdapter;
import com.mhike.m_hike.classes.tables.ObservationTable;

import java.util.ArrayList;
import java.util.List;

public class ViewHikeObservationActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private ArrayList<Observation> observationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        observationList = (ArrayList<Observation>) getHikeListData();

        RecyclerView recyclerView = findViewById(R.id.observation_recyclerview);
        ObservationAdapter adapter = new ObservationAdapter(
                observationList,
                ViewHikeObservationActivity.this,
                context,
                ActivityForm.HIKE_RECYCLER
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @SuppressLint("Range")
    private List<Observation> getHikeListData() {
        List<Observation> list = new ArrayList<>();
        String hikeID = getIntent().getStringExtra("hikeID");
        String userID = String.valueOf(getUserID());
        setTitle("Observation details for " + db.getHikeNameListByID(hikeID));

        try (Cursor cursor = db.getObservationList(userID, hikeID)) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return list;
            }

            while (cursor.moveToNext()) {
                list.add(new Observation(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_ID))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_HIKE_ID))),
                        cursor.getString(cursor.getColumnIndex(ObservationTable.OBSERVATION_TITLE)),
                        cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(ObservationTable.COLUMN_TIME)),
                        true
                ));

            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;


    }

    private int getUserID() {
        SharedPreferences preferences = getSharedPreferences(AccountPreferences.LOGIN_SHARED_PREF, MODE_PRIVATE);
        return preferences.getInt(AccountPreferences.USERID, 0);
    }


}
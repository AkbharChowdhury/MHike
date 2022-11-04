package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.HikeAdapter;
import com.mhike.m_hike.classes.tables.HikeTable;

import java.util.ArrayList;

public class HikeActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;

    private RecyclerView recyclerView;
    private ArrayList<String> hikeName;
    private ArrayList<String>hikeDescription;
    private ArrayList<String> hikeDate;
    private ArrayList<String> hikeID;


    private HikeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike);
        setTitle(getString(R.string.nav_hikes));
        context = getApplicationContext();
        FloatingActionButton btnAddHike = findViewById(R.id.btn_add_hike);
        btnAddHike.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddHikeActivity.class)));
        db = DatabaseHelper.getInstance(context);

        hikeID = new ArrayList<>();
        hikeName = new ArrayList<>();
        hikeDescription = new ArrayList<>();
        hikeDate = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new HikeAdapter(
                context,
                hikeID,
                hikeName,
                hikeDescription,
                hikeDate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showHikeList();

    }

    @SuppressLint("Range")
    private void showHikeList() {

        try(Cursor cursor = db.getHikeList()) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return;
            }

            String s = "";
//            // populate recycler view from database
            while (cursor.moveToNext()) {
                s+=cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME));


                hikeID.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID)));
                hikeName.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)));
                hikeDescription.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)));
                hikeDate.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE)));
            }
            Helper.longToastMessage(context, hikeName.toString());


        } catch (Exception ex){
            ex.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_logout){
            AccountPreferences.logout(context);
        }
        if(item.getItemId() == R.id.nav_hike){
            startActivity(new Intent(context, HikeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }



//    // refresh the activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            recreate();
//        }
//    }

}
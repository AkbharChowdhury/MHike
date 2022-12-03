package com.mhike.m_hike.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.adapters.SearchAdapter;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class SearchHikeActivity extends AppCompatActivity {

    // creating variables for
    // our ui components.
    private RecyclerView searchRecyclerView;
    DatabaseHelper db;
    Context context;
    // variable for our adapter
    // class and array list
    private SearchAdapter adapter;
    private ArrayList<Hike> hikeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hike);
        setTitle(R.string.search_hike);
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());

        db = DatabaseHelper.getInstance(context);
        // initializing our variables.
        searchRecyclerView = findViewById(R.id.search_recycler);

        // calling method to
        // build recycler view.
        buildRecyclerView();
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Hike> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Hike item : hikeList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getHikeName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        hikeList = (ArrayList<Hike>) getHikeListData();
        adapter = new SearchAdapter(hikeList, context, SearchHikeActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchRecyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        searchRecyclerView.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        searchRecyclerView.setAdapter(adapter);


    }

    @SuppressLint("Range")
    private List<Hike> getHikeListData() {
        List<Hike> list = new ArrayList<>();
        List<String> difficultyList = db.populateDropdown(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_TYPE);


        try (Cursor cursor = db.getHikeList(String.valueOf(getUserID()))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return list;
            }

            while (cursor.moveToNext()) {
                double distance = cursor.getDouble(cursor.getColumnIndex(HikeTable.COLUMN_DISTANCE));
                double elevationGain = cursor.getDouble(cursor.getColumnIndex(HikeTable.COLUMN_ELEVATION_GAIN));


                int difficultyLevel = Helper.getDifficultyLevel(distance, elevationGain);
                String difficultyName = difficultyList.get(difficultyLevel);

                list.add(new Hike(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID))),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE)),
                        difficultyName,
                        difficultyLevel

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
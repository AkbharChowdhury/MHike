package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

//import com.google.gson.Gson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.AccountPreferences;
import com.mhike.m_hike.classes.Address;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Employee;
import com.mhike.m_hike.classes.FamilyMember;
import com.mhike.m_hike.classes.HikeJson;
import com.mhike.m_hike.classes.JsonThread;
import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.models.User;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.utilities.Helper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UploadHikeActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = UploadHikeActivity.this;
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

        browser = (WebView) findViewById(R.id.webkit);

        getJsonData();
//
//        Gson gson = new Gson();
//        List<Hike> hikes = new ArrayList<>();
////        hikes.add(new Hike(
////                1,
////                "Snowdon",
////                "snow down description",
////               "2022-11-12"
////
////        ));
////        hikes.add(new Hike(
////                2,
////                "Trosley Country Park",
////                "Trosley Country Park description",
////                "2022-11-15"
////
////        ));
//        List<Hike> hikeListData = getHikeListData();
//
////        StringBuilder s = new StringBuilder();
////
////
////
////        for (Hike hike : hikeListData){
////            s.append("Hike name ").append(hike.getHikeName()).append("\n");
////            s.append("Hike date ").append(hike.getHikeDate()).append("\n");
////
////
////        }
//
////        Helper.longToastMessage(context,s.toString());
//        try {
//            String json = gson.toJson(hikeListData);
//        } catch (Exception ex){
//             ex.printStackTrace();
//            Log.d("HikeJSONUploadError","there was an error converting json data");
//            Log.d("HikeJSONUploadErrorMsg",ex.getMessage());
//
//            Helper.longToastMessage(context,ex.getMessage());
//        }
////        String hikeJson = gson.toJson(hikes);
////        Log.d("HIKES", String.valueOf(hikes.));
////        String json = gson.toJson();
////        String hikeListJson = gson.toJson(getHikeListData());
////        Log.d("Hike details", hikeListJson);
////        Helper.longToastMessage(context,hikeListJson);
////        uploadUserHikeDetails();
    }

    private void getJsonData() {

        try {
            Gson gson = new Gson();


            Address address = new Address("Germany", "Berlin");
            List<FamilyMember> family = new ArrayList<>();
            family.add(new FamilyMember("Wife", 30));
            family.add(new FamilyMember("Daughter", 5));
            Employee employee = new Employee("John", 30, "john@gmail.com", address, family);


            List<Hike> hikeListData = getHikeListData();
//            String json = gson.toJson(family);

            List<FamilyMember> h = new ArrayList<>();
            h.add(new FamilyMember("SnowDown"));
            h.add(new FamilyMember("Trosley Country Park"));
//            List<HikeJson> userHikes = new ArrayList<>();
            List<HikeJson> userHikes = getData();

//            userHikes.add(new HikeJson("SnowDown 22", "2022-11-12","a nice place"));
//            userHikes.add(new HikeJson("Trosley Country Park", "2022-11-15","a cool place"));

            //        hikes.add(new Hike(
//                2,
//                "Trosley Country Park",
//                "Trosley Country Park description",
//                "2022-11-15"
//
//        ));

            String hikeJson = gson.toJson(userHikes);
            Helper.longToastMessage(context, String.valueOf(hikeJson));

            Log.d("MyData", String.valueOf(hikeJson));

//            Helper.longToastMessage(context, json);

//            String json3 = gson.toJson();


//            Log.d("data", json3);
//
//            Helper.longToastMessage(context, json3);
        } catch (Exception ex) {
            Log.d("HikeJSONUploadError", "there was an error converting json data");
            Log.d("HikeJSONUploadErrorMsg", ex.getMessage());
            Helper.longToastMessage(context, ex.getMessage());

        }


//
//        String json = "[{\"age\":30,\"role\":\"Wife\"},{\"age\":5,\"role\":\"Daughter\"}]";
//
//        Type familyType = new TypeToken<ArrayList<FamilyMember>>() {}.getType();
//        ArrayList<FamilyMember> family2 = gson.fromJson(json, familyType);


    }

    private void uploadUserHikeDetails() {
        try {
            URL pageURL = new URL(getString(R.string.web_service_url));
            JsonThread.trustAllHosts();
            HttpURLConnection con =
                    (HttpURLConnection) pageURL.openConnection();
            // this must come from the database
            String jsonString = getString(R.string.json);

            JsonThread myTask = new JsonThread(this, con, jsonString);
            Thread t1 = new Thread(myTask, "JSON Thread");
            t1.start();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @SuppressLint("Range")
    private List<Hike> getHikeListData() {
        List<Hike> list = new ArrayList<>();
        int userID = User.getUserID(getApplicationContext());


        try (Cursor cursor = db.getHikeList(String.valueOf(userID))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes));
                return list;
            }

            while (cursor.moveToNext()) {
                list.add(new Hike(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID))),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE))

                ));

            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;


    }


    @SuppressLint("Range")
    private List<HikeJson> getData() {
        List<HikeJson> list = new ArrayList<>();
        int userID = User.getUserID(getApplicationContext());


        try (Cursor cursor = db.getHikeList(String.valueOf(userID))) {

            if (cursor.getCount() == 0) {
                Helper.longToastMessage(context, getString(R.string.no_hikes_web_service));
                return list;
            }

            while (cursor.moveToNext()) {
                int difficultyID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID)));

                        String difficultyStr = db.getColumnName(DifficultyTable.TABLE_NAME, DifficultyTable.COLUMN_ID, String.valueOf(difficultyID), DifficultyTable.COLUMN_TYPE);

                list.add(new HikeJson(

                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_Hike_NAME)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE)),
                        difficultyStr


                ));

            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;


    }
}
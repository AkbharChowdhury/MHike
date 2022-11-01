package com.mhike.m_hike.classes;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mhike.m_hike.classes.tables.HikePhoto;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ObservationTable;
import com.mhike.m_hike.classes.tables.UserTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MHike.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    private static DatabaseHelper instance;
    private int dbUserID = 0;


    //synchronized makes the app thread save
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // must be writable for queries to run from on create method
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createAllTables(db);
        
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    public String getDetails() {
        Cursor results = db.query("details", new String[]{"person_id", "name", "dob", "email"},
                null, null, null, null, "name");

        String resultText = "";

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            String dob = results.getString(2);
            String email = results.getString(3);

            resultText += id + " " + name + " " + dob + " " + email + "\n";

            results.moveToNext();
        }

        return resultText;

    }

    private void createAllTables(SQLiteDatabase db) {
        createUserTable(db);
        createHikeTable(db);
        createHikePhotoTable(db);
        createObservationTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserTable.TABLE_NAME + " ("
                + UserTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserTable.COLUMN_FIRSTNAME + " TEXT NOT NULL,"
                + UserTable.COLUMN_LASTNAME + " TEXT NOT NULL,"
                + UserTable.COLUMN_EMAIL + " TEXT NOT NULL UNIQUE,"
                + UserTable.COLUMN_PASSWORD + " TEXT NOT NULL,"
                + UserTable.COLUMN_REGISTERED_DATE + " TEXT NOT NULL)"
        );

    }


    private void createHikeTable(SQLiteDatabase db) {
        final String COLUMN_USER_ID = UserTable.COLUMN_ID;

        db.execSQL("CREATE TABLE " + HikeTable.TABLE_NAME + " ("
                + HikeTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HikeTable.COLUMN_USER_ID + " INTEGER NOT NULL,"
                + HikeTable.COLUMN_Hike_NAME + " TEXT NOT NULL,"
                + HikeTable.COLUMN_LOCATION + " TEXT NOT NULL,"
                + HikeTable.COLUMN_HIKE_DATE + " TEXT NOT NULL,"
                + HikeTable.COLUMN_PARKING_AVAILABLE + " TEXT NOT NULL,"
                + HikeTable.COLUMN_LENGTH + " NUMERIC NOT NULL,"
                + HikeTable.COLUMN_DIFFICULTY + " TEXT NOT NULL,"
                + HikeTable.COLUMN_DESCRIPTION + " TEXT,"
                + HikeTable.COLUMN_DURATION + " TEXT NOT NULL,"
                + HikeTable.COLUMN_FACILITY + " TEXT NOT NULL,"
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + COLUMN_USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE " +
                ")"
        );

    }


    private void createHikePhotoTable(SQLiteDatabase db) {
        final String COLUMN_HIKE_ID = HikePhoto.COLUMN_HIKE_ID;
        db.execSQL("CREATE TABLE " + HikePhoto.TABLE_NAME + " ("
                + HikePhoto.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HikePhoto.COLUMN_HIKE_ID + " INTEGER NOT NULL,"
                + HikePhoto.COLUMN_PHOTO + " TEXT NOT NULL,"

                + "FOREIGN KEY (" + COLUMN_HIKE_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + COLUMN_HIKE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE " +
                ")"
        );

    }


    private void createObservationTable(SQLiteDatabase db) {
        final String COLUMN_HIKE_ID = HikePhoto.COLUMN_HIKE_ID;
        db.execSQL("CREATE TABLE " + ObservationTable.TABLE_NAME + " ("
                + ObservationTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ObservationTable.COLUMN_HIKE_ID + " INTEGER NOT NULL,"
                + ObservationTable.COLUMN_OBSERVATION + " TEXT NOT NULL,"
                + ObservationTable.COLUMN_DATE_TIME + " TEXT NOT NULL,"
                + ObservationTable.COLUMN_COMMENTS + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_HIKE_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + COLUMN_HIKE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE " +
                ")"
        );

    }


}


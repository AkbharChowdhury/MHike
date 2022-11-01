package com.mhike.m_hike.classes;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.UserTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MHike.db";

    public static final String NAME_COLUMN = "name";
    public static final String DOB_COLUMN = "dob";
    public static final String EMAIL_COLUMN = "email";

    private final SQLiteDatabase database;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createHikeTable(db);

//        String query = "CREATE TABLE " + UserTable.TABLE_NAME + " ("
//                + UserTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + UserTable.COLUMN_FIRSTNAME + " TEXT,"
//                + UserTable.COLUMN_LASTNAME + " TEXT,"
//                + UserTable.COLUMN_EMAIL + " TEXT,"
//                + UserTable.COLUMN_PASSWORD + " TEXT)";
//
//        db.execSQL(query);
//        db.execSQL(DATABASE_CREATE);
//        createUserTable(db);
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

    public long insertDetails(String name, String dob, String email) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, name);
        rowValues.put(DOB_COLUMN, dob);
        rowValues.put(EMAIL_COLUMN, email);

        return database.insertOrThrow(DATABASE_NAME, null, rowValues);
    }

    public String getDetails() {
        Cursor results = database.query("details", new String[] {"person_id", "name", "dob", "email"},
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


    private void createHikeTable(SQLiteDatabase db) {
        String COLUMN_USER_ID = UserTable.COLUMN_ID;


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
                +"FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + COLUMN_USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE " +
                ")"
        );

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
}


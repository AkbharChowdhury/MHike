package com.mhike.m_hike.classes;

import android.annotation.SuppressLint;
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

import java.time.LocalDate;

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


    /************************************************** Create table statements ****************************************/
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


    /************************************************** Create Login/ register ****************************************/

    public boolean emailExists(String email) {
        String[] columns = {UserTable.COLUMN_EMAIL};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = UserTable.COLUMN_EMAIL + " LIKE ?";
        String[] selectionArgs = {"%" + email + "%"};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = ?
         */
        try (Cursor cursor = db.query(UserTable.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null)) {
            int cursorCount = cursor.getCount();
            return cursorCount > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    @SuppressLint("Range")
    public boolean isAuthorised(String email, String password) {
        // array of columns to fetch
        String[] columns = {UserTable.COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        // selection criteria
        String selection = UserTable.COLUMN_EMAIL + " = ?" + " AND " + UserTable.COLUMN_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */

        try (Cursor cursor = db.query(UserTable.TABLE_NAME, //Table to query
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null)) {

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    dbUserID = cursor.getInt(cursor.getColumnIndex(UserTable.COLUMN_ID));
                }

            }

            // count if the number of
            return cursor.getCount() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        LocalDate currentDate = LocalDate.now();

        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_FIRSTNAME, user.getFirstname());
        cv.put(UserTable.COLUMN_LASTNAME, user.getLastname());
        cv.put(UserTable.COLUMN_EMAIL, user.getEmail());
        cv.put(UserTable.COLUMN_PASSWORD, Encryption.encode(user.getPassword()));
        cv.put(UserTable.COLUMN_REGISTERED_DATE, String.valueOf(currentDate));

        long result = db.insert(UserTable.TABLE_NAME, null, cv);
        return result != -1;

    }


    public int getUserID(){
        return dbUserID;
    }


}


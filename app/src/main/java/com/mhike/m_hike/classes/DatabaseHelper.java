package com.mhike.m_hike.classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mhike.m_hike.classes.models.Hike;
import com.mhike.m_hike.classes.models.Observation;
import com.mhike.m_hike.classes.models.User;
import com.mhike.m_hike.classes.tables.DifficultyTable;
import com.mhike.m_hike.classes.tables.HikePhoto;
import com.mhike.m_hike.classes.tables.HikeTable;
import com.mhike.m_hike.classes.tables.ObservationTable;
import com.mhike.m_hike.classes.tables.ParkingTable;
import com.mhike.m_hike.classes.tables.UserTable;
import com.mhike.m_hike.utilities.Encryption;
import com.mhike.m_hike.utilities.Helper;

import java.util.ArrayList;
import java.util.List;

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
        addDefaultTableValues(db);

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

    private void addDefaultTableValues(SQLiteDatabase db) {
        addDifficulty(db);
        addParking(db);
        addUser(db);
    }


    /************************************************** Create table statements ****************************************/

    private void createAllTables(SQLiteDatabase db) {
        createUserTable(db);
        createDifficultyTable(db);
        createParkingTable(db);
        createHikeTable(db);
        createHikePhotoTable(db);
        createObservationTable(db);


    }

    private void addDifficulty(SQLiteDatabase db) {
        String[] difficultyList = {
                "Easy",
                "Moderate",
                "Hard",
                "Expert"

        };

        for (String difficulty : difficultyList) {

            ContentValues values = new ContentValues();
            values.put(DifficultyTable.COLUMN_TYPE, difficulty);
            db.insert(DifficultyTable.TABLE_NAME, null, values);

        }
    }

    private void addParking(SQLiteDatabase db) {
        String[] parkingList = {
                "Yes",
                "No"
        };

        for (String parking : parkingList) {

            ContentValues values = new ContentValues();
            values.put(ParkingTable.COLUMN_TYPE, parking);
            db.insert(ParkingTable.TABLE_NAME, null, values);

        }
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


    private void createDifficultyTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DifficultyTable.TABLE_NAME + " ("
                + DifficultyTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DifficultyTable.COLUMN_TYPE + " TEXT NOT NULL UNIQUE)"
        );

    }

    private void createParkingTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ParkingTable.TABLE_NAME + " ("
                + ParkingTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ParkingTable.COLUMN_TYPE + " TEXT NOT NULL UNIQUE)"
        );

    }


    private void createHikeTable(SQLiteDatabase db) {
        final String COLUMN_USER_ID = UserTable.COLUMN_ID;
        final String COLUMN_PARKING_ID = ParkingTable.COLUMN_ID;
        final String COLUMN_DIFFICULTY_ID = DifficultyTable.COLUMN_ID;


        db.execSQL("CREATE TABLE " + HikeTable.TABLE_NAME + " ("
                + HikeTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HikeTable.COLUMN_USER_ID + " INTEGER NOT NULL,"
                + HikeTable.COLUMN_HIKE_DATE + " TEXT NOT NULL,"
                + HikeTable.COLUMN_HIKE_NAME + " TEXT NOT NULL,"
                + HikeTable.COLUMN_DESCRIPTION + " TEXT,"
                + HikeTable.COLUMN_LOCATION + " TEXT NOT NULL,"
                + HikeTable.COLUMN_DISTANCE + " NUMERIC NOT NULL,"
                + HikeTable.COLUMN_DURATION + " NUMERIC NOT NULL,"

                + HikeTable.COLUMN_PARKING_ID + " INTEGER NOT NULL,"
                + HikeTable.COLUMN_ELEVATION_GAIN + " NUMERIC NOT NULL,"
                + HikeTable.COLUMN_HIGH + " NUMERIC NOT NULL,"
                + HikeTable.COLUMN_DIFFICULTY_ID + " INTEGER NOT NULL,"

                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + COLUMN_USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMN_PARKING_ID + ") REFERENCES " + ParkingTable.TABLE_NAME + "(" + COLUMN_PARKING_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMN_DIFFICULTY_ID + ") REFERENCES " + DifficultyTable.TABLE_NAME + "(" + COLUMN_DIFFICULTY_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
                + ")"
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
        final String COLUMN_HIKE_ID = HikeTable.COLUMN_ID;
        db.execSQL("CREATE TABLE " + ObservationTable.TABLE_NAME + " ("
                + ObservationTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ObservationTable.COLUMN_HIKE_ID + " INTEGER NOT NULL,"
                + ObservationTable.OBSERVATION_TITLE + " TEXT NOT NULL,"
                + ObservationTable.COLUMN_COMMENTS + " TEXT,"
                + ObservationTable.COLUMN_DATE + " TEXT NOT NULL,"
                + ObservationTable.COLUMN_TIME + " TEXT NOT NULL,"
                + "FOREIGN KEY (" + COLUMN_HIKE_ID + ") REFERENCES " + HikeTable.TABLE_NAME + "(" + COLUMN_HIKE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE " +
                ")"
        );

    }

    /************************************************** insert default values into table ****************************************/


    /************************************************** Create Login/ register ****************************************/

    public boolean columnExists(String fieldValue, String column, String table) {
        String[] columns = {column};
        SQLiteDatabase db = getReadableDatabase();
        String selection = column + " LIKE ?";
        String[] selectionArgs = { fieldValue };
        // select the email field and compare it to the entered email
        try (Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null)) {
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

        // query to check if user email and password is correct
        try (Cursor cursor = db.query(UserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    dbUserID = cursor.getInt(cursor.getColumnIndex(UserTable.COLUMN_ID));
                }

            }
            return cursor.getCount() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }


    @SuppressLint("Range")
    public boolean userHikeNameExists(String hikeName, String userID) {
        // array of columns to fetch
        String[] columns = {HikeTable.COLUMN_HIKE_NAME, UserTable.COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        // selection criteria
        String selection = HikeTable.COLUMN_HIKE_NAME + " LIKE ?" + " AND " + UserTable.COLUMN_ID + " = ?";
        // selection arguments
        String[] selectionArgs = {hikeName, userID};

        try (Cursor cursor = db.query(HikeTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {


            return cursor.getCount() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }


    /**
     * retrieves the user first and last name- for dashboard functionality
     * */
    @SuppressLint("Range")
    public User getUserFirstAndLastName(String userID) {
        // array of columns to fetch
        String[] columns = {UserTable.COLUMN_FIRSTNAME, UserTable.COLUMN_LASTNAME};
        SQLiteDatabase db = getReadableDatabase();
        // selection criteria
        String selection = UserTable.COLUMN_ID + " = ?";
        // selection arguments
        String[] selectionArgs = {userID};
        User user = new User();

        // query to check if user email and password is correct
        try (Cursor cursor = db.query(UserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    user.setFirstname(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_FIRSTNAME)));
                    user.setLastname(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_LASTNAME)));

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            return user;

        }
        return user;

    }


    /**
     * Register user details
     * */
    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_FIRSTNAME, user.getFirstname());
        cv.put(UserTable.COLUMN_LASTNAME, user.getLastname());
        cv.put(UserTable.COLUMN_EMAIL, user.getEmail());
        cv.put(UserTable.COLUMN_PASSWORD, Encryption.encode(user.getPassword()));
        cv.put(UserTable.COLUMN_REGISTERED_DATE, String.valueOf(Helper.getCurrentDate()));

        long result = db.insert(UserTable.TABLE_NAME, null, cv);
        return result != -1;

    }


    /**
     * add single hike to the hike table
     * */
    public boolean addHike(Hike hike, int userID) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HikeTable.COLUMN_USER_ID, userID);
        cv.put(HikeTable.COLUMN_HIKE_DATE, hike.getHikeDate());
        cv.put(HikeTable.COLUMN_HIKE_NAME, hike.getHikeName());
        cv.put(HikeTable.COLUMN_DESCRIPTION, hike.getDescription());
        cv.put(HikeTable.COLUMN_LOCATION, hike.getLocation());
        cv.put(HikeTable.COLUMN_DISTANCE, hike.getDistance());
        cv.put(HikeTable.COLUMN_DURATION, hike.getDuration());
        cv.put(HikeTable.COLUMN_PARKING_ID, hike.getParkingID());
        cv.put(HikeTable.COLUMN_ELEVATION_GAIN, hike.getElevationGain());
        cv.put(HikeTable.COLUMN_HIGH, hike.getHigh());
        cv.put(HikeTable.COLUMN_DIFFICULTY_ID, hike.getDifficultyID());
        long result = db.insert(HikeTable.TABLE_NAME, null, cv);
        return result != -1;

    }

    /**
     * add a list of observation
     * */
    public void addObservation(List<Observation> observationList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (Observation observation : observationList) {
            ContentValues cv = new ContentValues();
            cv.put(ObservationTable.COLUMN_HIKE_ID, observation.getHikeID());
            cv.put(ObservationTable.OBSERVATION_TITLE, observation.getObservation());
            cv.put(ObservationTable.COLUMN_COMMENTS, observation.getComments());
            cv.put(ObservationTable.COLUMN_DATE, observation.getObservationDate());
            cv.put(ObservationTable.COLUMN_TIME, observation.getObservationTime());
            db.insert(ObservationTable.TABLE_NAME, null, cv);
        }

    }


    /**
     * add default user to the User table
     * */
    public void addUser(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_FIRSTNAME, "tom");
        cv.put(UserTable.COLUMN_LASTNAME, "Smith");
        cv.put(UserTable.COLUMN_EMAIL, "tom@gmail.com");
        cv.put(UserTable.COLUMN_PASSWORD, Encryption.encode("password"));
        cv.put(UserTable.COLUMN_REGISTERED_DATE, String.valueOf(Helper.getCurrentDate()));
        db.insert(UserTable.TABLE_NAME, null, cv);

    }

    @SuppressLint("Range")
    public List<String> populateDropdown(String tableName, String column) {
        List<String> list = new ArrayList<>();

        String selectQuery = "SELECT " + column + " FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(column))); // get the type field
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return list;
    }
    @SuppressLint("Range")
    public List<String> getUserHikes(String userID) {
        List<String> list = new ArrayList<>();

        String selectQuery =
             "SELECT " + HikeTable.COLUMN_HIKE_NAME + " FROM " + HikeTable.TABLE_NAME + " WHERE " + HikeTable.COLUMN_USER_ID + " =?";
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(selectQuery, new String[]{userID}, null)) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_NAME))); // get the type field
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return list;
    }


    @SuppressLint("Range")
    public List<String> getHikeNameListByID(String hikeID) {
        List<String> list = new ArrayList<>();

        String selectQuery =
                "SELECT " + HikeTable.COLUMN_HIKE_NAME + " FROM " + HikeTable.TABLE_NAME + " WHERE " + HikeTable.COLUMN_ID +"=?";
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(selectQuery, new String[]{hikeID}, null)) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_NAME))); // get the type field
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return list;
    }




    public Cursor getHikeList(String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        return db != null ? db.rawQuery("SELECT * FROM " + HikeTable.TABLE_NAME + " WHERE "+ HikeTable.COLUMN_USER_ID + " =?" + "ORDER BY hike_date", new String[]{userID}, null) : null;
    }

    public Cursor getHikeListObservation(String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        return db != null ? db.rawQuery("SELECT DISTINCT  o.hike_id,\n" +
                "h.hike_name,\n" +
                "h.description,\n" +
                "h.hike_date\n" +
                "\n" +
                "FROM observation o\n" +
                "JOIN Hike h ON h.hike_id = o.hike_id\n" +
                "WHERE h.user_id = ?", new String[]{userID}, null) : null;
    }



    public Cursor getObservationList(String userID, String hikeID){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = String.format(
                "SELECT o.* FROM %s o JOIN %s h ON h.%s = o.%s WHERE h.%s = ? AND o.%s = ?",
                ObservationTable.TABLE_NAME,
                HikeTable.TABLE_NAME,
                HikeTable.COLUMN_ID,
                ObservationTable.COLUMN_HIKE_ID,
                HikeTable.COLUMN_USER_ID,
                ObservationTable.COLUMN_HIKE_ID);
        return db != null ? db.rawQuery(sql, new String[]{userID, hikeID}, null) : null;
    }



    @SuppressLint("Range")
    public List<Hike>  getSelectedHike(String hikeID) {
        List<Hike> hikeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HikeTable.TABLE_NAME + " WHERE "+ HikeTable.COLUMN_ID + " =?", new String[]{hikeID});

        if (cursor.moveToLast()) {

            Hike hike = new Hike();
            hike.setHikeID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ID))));
            hike.setHikeDate(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_DATE)));
            hike.setHikeName(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIKE_NAME)));
            hike.setDescription(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DESCRIPTION)));
            hike.setLocation(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_LOCATION)));
            hike.setDistance(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DISTANCE))));
            hike.setDuration(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DURATION))));
            hike.setParkingID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_PARKING_ID))));
            hike.setElevationGain(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_ELEVATION_GAIN))));
            hike.setHigh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_HIGH))));
            hike.setDifficultyID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HikeTable.COLUMN_DIFFICULTY_ID))));

            hikeList.add(hike);

        }

        return hikeList;
    }



    @SuppressLint("Range")
    public  int  getHikeIDByName(String hikeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HikeTable.TABLE_NAME + " WHERE "+ HikeTable.COLUMN_HIKE_NAME + " =?", new String[]{hikeName});

        if (cursor.moveToLast()) {
           return cursor.getInt(cursor.getColumnIndex(ObservationTable.COLUMN_HIKE_ID));



        }

        return 0;
    }

    @SuppressLint("Range")
    public String getColumnName(String table, String columnID, String id, String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE " + columnID + " =?", new String[]{id});


        if (cursor.moveToLast()) {
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        return null;
    }







    public int getColumnID(String tableName, String column, String idField, String value) {
        int id = 0;
        String[] columns = {idField};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = column + " = ?";
        String[] selectionArgs = {value};


        try (Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null)) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getInt(0);
            }
            return id;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    public int getUserID() {
        return dbUserID;
    }

    public boolean updateHike(Hike hike, String hikeID, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HikeTable.COLUMN_USER_ID, userID);
        cv.put(HikeTable.COLUMN_HIKE_DATE, hike.getHikeDate());
        cv.put(HikeTable.COLUMN_HIKE_NAME, hike.getHikeName());
        cv.put(HikeTable.COLUMN_DESCRIPTION, hike.getDescription());
        cv.put(HikeTable.COLUMN_LOCATION, hike.getLocation());
        cv.put(HikeTable.COLUMN_DISTANCE, hike.getDistance());
        cv.put(HikeTable.COLUMN_DURATION, hike.getDuration());
        cv.put(HikeTable.COLUMN_PARKING_ID, hike.getParkingID());
        cv.put(HikeTable.COLUMN_ELEVATION_GAIN, hike.getElevationGain());
        cv.put(HikeTable.COLUMN_HIGH, hike.getHigh());
        cv.put(HikeTable.COLUMN_DIFFICULTY_ID, hike.getDifficultyID());

        long hikeUpdated = db.update(HikeTable.TABLE_NAME, cv, HikeTable.COLUMN_ID + "=?", new String[]{hikeID});
        return hikeUpdated != -1;


    }
    public boolean deleteRecord(String table, String idField, String id){
        String whereClause = idField + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        long result = db.delete(table, whereClause, whereArgs);
        return result!= -1;
    }


}


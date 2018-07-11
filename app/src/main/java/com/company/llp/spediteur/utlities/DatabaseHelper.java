package com.company.llp.spediteur.utlities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "SETTINGS_TABLE";
    private static final String TAG = "DatabaseHelper";
    // Table columns
    public static final String _ID = "_id";
    public static final String APP_ID = "app_id";
    public static final String SERVER_URL = "server_url";
    public static final String EXTRACTION_KEY = "extraction_key";
    public static final String IS_ENABLED = "is_enabled";


    // Database Information
    static final String DB_NAME = "SPEDITEUR.DB";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE    " + TABLE_NAME + " ( "
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            APP_ID + "    INTEGER  (20)     NOT NULL, " +
            SERVER_URL + "     VARCHAR  (255)  NOT NULL, " +
            EXTRACTION_KEY + "   VARCHAR (25) ," +
            IS_ENABLED + "      boolean "+
            ");";

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        Log.e(TAG,"Creating DB");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.e(TAG,"Creating DB");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

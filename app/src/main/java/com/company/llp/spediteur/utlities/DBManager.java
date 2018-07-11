package com.company.llp.spediteur.utlities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.company.llp.spediteur.common.model.SettingBean;

public class DBManager {
    private static final String TAG = "DBManager";
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
        dbHelper = new DatabaseHelper(context);

    }

    public DBManager open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
}

    public void close() {
        dbHelper.close();
    }
    public boolean checkIfOpened() {
        return database!=null && database.isOpen();
    }
    public long insert(SettingBean bean) {
        Log.e(TAG,"Insert");
        deleteWithKey(bean.getAppId());
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.APP_ID, bean.getAppId());
        contentValue.put(DatabaseHelper.EXTRACTION_KEY, bean.getExtractionKey());
        contentValue.put(DatabaseHelper.SERVER_URL, bean.getForwardURL());
        contentValue.put(DatabaseHelper.IS_ENABLED, bean.isEnabled());

       return database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        Log.e(TAG,"fetch bean");

        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.APP_ID, DatabaseHelper.EXTRACTION_KEY, DatabaseHelper.SERVER_URL,DatabaseHelper.IS_ENABLED };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(SettingBean bean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.APP_ID, bean.getAppId());
        contentValues.put(DatabaseHelper.EXTRACTION_KEY, bean.getExtractionKey());
        contentValues.put(DatabaseHelper.SERVER_URL, bean.getForwardURL());
        contentValues.put(DatabaseHelper.IS_ENABLED, bean.isEnabled());
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + bean.getId(), null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deleteAll() {
        database.execSQL("DELETE FROM  "+DatabaseHelper.TABLE_NAME);
        //database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
    public void deleteWithKey(int appid) {
        database.execSQL("DELETE FROM  "+DatabaseHelper.TABLE_NAME+ " WHERE "+DatabaseHelper.APP_ID+" = "+appid);
        //database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
    public Cursor getSettingOne(int appId) {
        Cursor cursor = null;
        String empName = "";
        try {
            cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME+" WHERE "+DatabaseHelper.APP_ID+"=?", new String[] {appId + ""});

            return cursor;
        }finally {

        }
    }
}

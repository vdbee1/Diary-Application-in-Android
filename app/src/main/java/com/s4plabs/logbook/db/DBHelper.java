package com.s4plabs.logbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "LocalLogs.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_DAYLOGS_TABLE = "CREATE TABLE " + DBContract.DayLogs.TABLE_NAME + " (" +
                DBContract.DayLogs.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "+
                DBContract.DayLogs.COLUMN_NAME_LOG + " TEXT, " +
                "UNIQUE (" + DBContract.DayLogs.COLUMN_NAME_ID + ") ON CONFLICT REPLACE );";
        sqLiteDatabase.execSQL(SQL_CREATE_DAYLOGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

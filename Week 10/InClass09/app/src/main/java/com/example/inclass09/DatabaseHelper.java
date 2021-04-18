package com.example.inclass09;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "grades.db";
    final static int DATABASE_VERSION = 7;
    public static String TAG = "app";


    public DatabaseHelper(Context mContext){
        super(mContext,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        GradeTable.onCreate(db);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        GradeTable.onUpgrade(db,oldVersion,newVersion);
        GradeTable.onCreate(db);
        Log.d(TAG, "onUpgrade: ");

    }
}

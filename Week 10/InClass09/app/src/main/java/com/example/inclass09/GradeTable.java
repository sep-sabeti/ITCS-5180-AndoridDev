package com.example.inclass09;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.security.identity.EphemeralPublicKeyNotFoundException;
import android.util.Log;

public class GradeTable {
    final static String TABLE_NAME = "grades";
    final static String COLUMN_ID = "_id";
    final static String COLUMN_COURSE_NUMBER = "courseNumber";
    final static String COLUMN_COURSE_NAME = "courseName";
    final static String COLUMN_COURSE_GRADE = "courseGrade";
    final static String COLUMN_COURSE_CREDIT_HOURS = "creditHours";
    final static String TAG = "app";


    //CREATE TABLE grades (_id integer primary key autoincrement, courseNumber text not null, courseName text not null, courseGrade text not null, courseCreditHourst integer not null);

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_COURSE_NUMBER + " text not null, " );
        sb.append(COLUMN_COURSE_NAME + " text not null, ");
        sb.append(COLUMN_COURSE_GRADE + " text not null, ");
        sb.append(COLUMN_COURSE_CREDIT_HOURS + " integer not null);");


        try{
            Log.d(TAG, "onCreate: "+ sb);
            db.execSQL(sb.toString());
            Log.d(TAG, "onCreate: ");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        try {
            db.execSQL("DROP TABLE IF EXISTS " + GradeTable.TABLE_NAME);
            Log.d(TAG, "onUpgrade: ");

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}

package com.example.inclass09;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;

public class DatabaseManager implements Serializable {

    Context mContext;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    GradeDAO DAO;

    public DatabaseManager(Context mContext){
        this.mContext = mContext;
        dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
        DAO = new GradeDAO(db);

    }


    public GradeDAO getDAO() {
        return DAO;
    }

    public void setDAO(GradeDAO DAO) {
        this.DAO = DAO;
    }
}

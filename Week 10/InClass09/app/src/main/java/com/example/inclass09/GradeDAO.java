package com.example.inclass09;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebHistoryItem;

import java.util.ArrayList;

public class GradeDAO {

    private SQLiteDatabase db;

    public GradeDAO(SQLiteDatabase db){
        this.db = db;
    }


    public long insert(Grade grade){

        ContentValues contentValues = new ContentValues();

        contentValues.put(GradeTable.COLUMN_COURSE_NUMBER , grade.getCourseNumber());
        contentValues.put(GradeTable.COLUMN_COURSE_NAME , grade.getCourseName());
        contentValues.put(GradeTable.COLUMN_COURSE_GRADE , grade.getCourseGrade());
        contentValues.put(GradeTable.COLUMN_COURSE_CREDIT_HOURS , grade.getCourseCreditHours());


         return db.insert(GradeTable.TABLE_NAME,null,contentValues);

    }

    public boolean update(Grade grade){
        ContentValues contentValues = new ContentValues();
        contentValues.put(GradeTable.COLUMN_COURSE_NUMBER , grade.getCourseNumber());
        contentValues.put(GradeTable.COLUMN_COURSE_NAME , grade.getCourseName());
        contentValues.put(GradeTable.COLUMN_COURSE_GRADE , grade.getCourseGrade());
        contentValues.put(GradeTable.COLUMN_COURSE_CREDIT_HOURS , grade.getCourseCreditHours());

       return db.update(GradeTable.TABLE_NAME,contentValues,GradeTable.COLUMN_ID + " = ?",new String[]{String.valueOf(grade.getId())}) > 0;
    }

    public boolean delete(Grade grade){

        return db.delete(GradeTable.TABLE_NAME,GradeTable.COLUMN_ID + " = ?",new String[]{String.valueOf(grade.getId())}) > 0 ;
    }

    public Grade getByID(long id){
        Grade grade = null;


        Cursor cursor = db.query(GradeTable.TABLE_NAME,new String[]{GradeTable.COLUMN_ID,GradeTable.COLUMN_COURSE_NUMBER,GradeTable.COLUMN_COURSE_NAME,
                GradeTable.COLUMN_COURSE_GRADE,GradeTable.COLUMN_COURSE_CREDIT_HOURS},GradeTable.COLUMN_ID + " = ?",new String[]{String.valueOf(id)
        },null,null,null);

       if(cursor.moveToFirst()){
            grade = cursorToGrade(cursor);

       }

        return grade;
    }

    public ArrayList<Grade> getAllGrades(){
        ArrayList<Grade> grades = new ArrayList<>();
        Cursor cursor = db.query(GradeTable.TABLE_NAME,
                new String[]{GradeTable.COLUMN_ID,GradeTable.COLUMN_COURSE_NUMBER,GradeTable.COLUMN_COURSE_NAME,
                        GradeTable.COLUMN_COURSE_GRADE,GradeTable.COLUMN_COURSE_CREDIT_HOURS},null,null,null,null,null);

        while (cursor.moveToNext()){
            grades.add(cursorToGrade(cursor));
        }

    return grades;
    }

    public double getGPA(){
        double numberOfCredits = 0;
        double gradeCrossedNumberOfCredits = 0;

        Cursor cursor = db.query(GradeTable.TABLE_NAME,
                new String[]{GradeTable.COLUMN_ID,GradeTable.COLUMN_COURSE_NUMBER,GradeTable.COLUMN_COURSE_NAME,
                        GradeTable.COLUMN_COURSE_GRADE,GradeTable.COLUMN_COURSE_CREDIT_HOURS},null,null,null,null,null);

        while (cursor.moveToNext()){
            Grade grade = cursorToGrade(cursor);
            numberOfCredits = numberOfCredits + grade.getCourseCreditHours();
            gradeCrossedNumberOfCredits = gradeCrossedNumberOfCredits + grade.getCourseCreditHours() * grade.getNumericGrade();
        }

        if(numberOfCredits != 0){
            return gradeCrossedNumberOfCredits / numberOfCredits;

        } else {
            return 0;
        }

    }

    public long getHours(){
        long numberOfCredits = 0;

        Cursor cursor = db.query(GradeTable.TABLE_NAME,
                new String[]{GradeTable.COLUMN_ID,GradeTable.COLUMN_COURSE_NUMBER,GradeTable.COLUMN_COURSE_NAME,
                        GradeTable.COLUMN_COURSE_GRADE,GradeTable.COLUMN_COURSE_CREDIT_HOURS},null,null,null,null,null);

        while (cursor.moveToNext()){
            Grade grade = cursorToGrade(cursor);
            numberOfCredits = numberOfCredits + grade.getCourseCreditHours();
        }

        return numberOfCredits;
    }
    private Grade cursorToGrade(Cursor cursor){
        Grade grade = new Grade();
        grade.setId(cursor.getLong(0));
        grade.setCourseNumber(cursor.getString(1));
        grade.setCourseName(cursor.getString(2));
        grade.setCourseGrade(cursor.getString(3));
        grade.setCourseCreditHours(cursor.getInt(4));
        return grade;
    };
}

package com.example.inclass09;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCourse.IAddACourseListener, GradeFragment.IGradeListListeer {

    DatabaseManager dm;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().show();

        getSupportActionBar().setTitle("Course");

//        setTitle(R.string.courses);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container , new GradeFragment())
                .commit();


    }


    @Override
    public void submitClicked(boolean status) {
//        setTitle(R.string.courses);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().show();


        getSupportActionBar().setTitle("Course");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new GradeFragment())
                .commit();

    }

    @Override
    public void cancelClicked(boolean status) {
//        setTitle(R.string.courses);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().show();


        getSupportActionBar().setTitle("Course");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new GradeFragment())
                .commit();
    }

    @Override
    public void addCourseClicked(boolean status) {
        if(status){
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().hide();


            getSupportActionBar().setTitle("Course");
            setTitle(R.string.addCourse);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new AddCourse())
                    .commit();
        }
    }

    @Override
    public void deleteClicked(boolean status) {
        if(status){
//            setTitle(R.string.courses);
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().show();

            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            getSupportActionBar().setTitle("Course");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new GradeFragment())
                    .commit();
        }
    }
}
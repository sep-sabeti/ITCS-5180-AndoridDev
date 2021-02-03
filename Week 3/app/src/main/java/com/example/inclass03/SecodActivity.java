package com.example.inclass03;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SecodActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button select, cancel;
    public static String selectedDepartment;
    public static String ENTERED_DATA = "Entered_Data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secod);
        radioGroup = findViewById(R.id.radioGroup);
        select = findViewById(R.id.select2);
        cancel = findViewById(R.id.cancel2);

        setTitle(R.string.title_reg);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cs){
                    selectedDepartment = getResources().getString(R.string.cs);
                }
                else if(checkedId == R.id.bio){
                    selectedDepartment = getResources().getString(R.string.bio);
                }
                else if(checkedId == R.id.sis){
                    selectedDepartment = getResources().getString(R.string.SIS);
                }
                else if(checkedId == R.id.ds){
                    selectedDepartment = getResources().getString(R.string.ds);
                }
            }
        });



    select.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedDepartment != null){
                Intent intent = new Intent();
                intent.putExtra(ENTERED_DATA,selectedDepartment);
                setResult(RESULT_OK,intent);
                Log.d("selectedDepartment", selectedDepartment );
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.missingRadioSelection),Toast.LENGTH_SHORT).show();
            }
        }
    });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
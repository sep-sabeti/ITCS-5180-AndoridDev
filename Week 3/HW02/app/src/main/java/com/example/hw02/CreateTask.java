package com.example.hw02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText txtCreateTaskName;
    RadioGroup grpPriority;
    Button btnCreateClose;
    Button btnSubmit;
    Button btnSetDate;
    DatePickerDialog datePickerDialog;
    TextView dateShow;
    public static Task task = null;
    Calendar calender;

    RadioButton high,medium,low;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        txtCreateTaskName =  findViewById(R.id.txtCreateTaskName);
        grpPriority =  findViewById(R.id.grpPriority);
        btnCreateClose = findViewById(R.id.btnCreateCancel);
        btnSubmit = findViewById(R.id.btnCreateSubmit);
        btnSetDate = findViewById(R.id.btnSetDate);
        task = new Task();
        dateShow = findViewById(R.id.dateShow);

        high = findViewById(R.id.radHigh);
        medium = findViewById(R.id.radMedium);
        low = findViewById(R.id.radLow);

        setTitle("Create Task");

        btnCreateClose.setOnClickListener(v ->
                CreateTask.this.finish());

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        btnSubmit.setOnClickListener((View.OnClickListener) v -> {
            if(checkInputEmpty() == true){
                task.setName(txtCreateTaskName.getText().toString());
                Intent toMainWithTask = new Intent();
                setResult(RESULT_OK, toMainWithTask);
                toMainWithTask.putExtra(MainActivity.GENERATED_TASK_NAME, task);
                finish();
            }

        });


        grpPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == high.getId()){
                    task.setPriority(getResources().getString(R.string.high));
                } else if (checkedId == medium.getId()){
                    task.setPriority(getResources().getString(R.string.medium));
                } else if(checkedId == low.getId()){
                    task.setPriority(getResources().getString(R.string.low));

                }
            }
        });

    }



    public boolean checkInputEmpty(){
        if(txtCreateTaskName.getText().toString().equals("")){
            Toast.makeText(this,getResources().getString(R.string.emptyTask),Toast.LENGTH_LONG).show();
            return false;
        } else if(task.getDate() == null) {
            Toast.makeText(this,getResources().getString(R.string.emptyDate),Toast.LENGTH_LONG).show();
            return false;
        } else if(task.getPriority() != getResources().getString(R.string.high) && task.getPriority() != getResources().getString(R.string.medium) && task.getPriority() != getResources().getString(R.string.low)){
            Toast.makeText(this,getResources().getString(R.string.emptyPriority),Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


    private void showDatePickerDialog(){
        datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,this,calender.getInstance().get(Calendar.YEAR),calender.getInstance().get(Calendar.MONTH),calender.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_500));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_500));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar date = Calendar.getInstance();
        date.set(year,month,dayOfMonth);
        task.setDate(date);
        dateShow.setText(task.toString());
    }
}
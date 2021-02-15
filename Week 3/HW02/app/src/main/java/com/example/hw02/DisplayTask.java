/*

DisplayTask.java
Used for the second activity screen to display the task
 */
package com.example.hw02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayTask extends AppCompatActivity {

    TextView nameView,dateView,prioView;
    Button cancelButton,deleteButton;
    public static Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);


        nameView = findViewById(R.id.nameView);
        dateView = findViewById(R.id.dateView);
        prioView = findViewById(R.id.prioView);

        cancelButton = findViewById(R.id.btnCancel);
        deleteButton = findViewById(R.id.btnDelete);


        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.SELECTED_TASK_NAME)){

            task = (Task) getIntent().getSerializableExtra(MainActivity.SELECTED_TASK_NAME);
            nameView.setText(task.getName());
            dateView.setText(task.toString());
            prioView.setText(task.getPriority());
        }


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayTask.this);

                builder.setTitle(getResources().getString(R.string.confirmation));
                builder.setMessage(getResources().getString(R.string.confirmationMessage));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });

                builder.create();
                builder.show();
            }
        });






    }
}
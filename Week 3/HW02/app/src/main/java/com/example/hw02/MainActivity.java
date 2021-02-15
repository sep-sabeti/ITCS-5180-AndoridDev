package com.example.hw02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    Button btnViewTasks;
    Button btnCreateTasks;
    TextView lblDisplayTasks , taskNumber , taskView , taskName, taskDate, taskPrio;

    final static public int REQ_CODE = 100;
    final static public int REQ_CODE_2 = 200;

    final static public String SELECTED_TASK_NAME = "Selected Task";
    final static public String GENERATED_TASK_NAME = "Generated Task";

    public static Task tobeDeletedTask = null;

    int taskIndex = 0; //initialize number of tasks to 0
    //add Task objects to array list
    ArrayList<Task> Tasks = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List");

        btnViewTasks = findViewById(R.id.btnViewTasks);
        btnCreateTasks = findViewById(R.id.btnCreateTask);
        lblDisplayTasks = findViewById(R.id.lblDisplayTasks);
        taskNumber = findViewById(R.id.taskNumber);
        taskNumber.setText("  " + String.valueOf(taskIndex) + "  ");
        taskView = findViewById(R.id.task);
        taskName = findViewById(R.id.taskName);
        taskDate = findViewById(R.id.taskDate);
        taskPrio = findViewById(R.id.taskPriority);


        btnCreateTasks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTask.class);
            startActivityForResult(intent, REQ_CODE);
        });


        btnViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.selectTask));


                ArrayList<String> taskNames = new ArrayList<>();

                for (Task task: Tasks
                     ) {
                    taskNames.add(task.getName());
                }

                if(Tasks.size() == 0){
                    builder.setMessage(getResources().getString(R.string.noTask));
                } else{
                    builder.setItems(taskNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this,DisplayTask.class);
                            intent.putExtra(SELECTED_TASK_NAME,Tasks.get(which));
                            tobeDeletedTask = Tasks.get(which);
                            startActivityForResult(intent,REQ_CODE_2);
                        }
                    });
                }

                builder.create();
                builder.show();

            }
        });


        };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK){
                Task newTask = (Task) data.getSerializableExtra(GENERATED_TASK_NAME);
                newTask.setId(taskIndex);
                Tasks.add(newTask);
                taskIndex++;

                if(taskIndex == 1){
                    taskView.setText(getResources().getString(R.string.task));
                }
                else{
                    taskView.setText(getResources().getString(R.string.tasks));
                }
                taskNumber.setText("  " + taskIndex+"  ");

                Collections.sort(Tasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

                taskName.setText(Tasks.get(0).getName());
                taskDate.setText(Tasks.get(0).toString());
                taskPrio.setText(Tasks.get(0).getPriority());
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this,getResources().getString(R.string.cancelTask),Toast.LENGTH_LONG).show();
            }
        }


        else if (requestCode == REQ_CODE_2){
            if(resultCode == RESULT_OK){
                taskIndex--;
                Tasks.remove(tobeDeletedTask.getID());
                Toast.makeText(this, getResources().getString(R.string.deleteToast), Toast.LENGTH_SHORT).show();
                if(Tasks.size() != 0 ){
                    if(taskIndex == 1){
                        taskView.setText(getResources().getString(R.string.task));
                    }
                    else{
                        taskView.setText(getResources().getString(R.string.tasks));
                    }
                    taskNumber.setText("  " + taskIndex+"  ");


                    Collections.sort(Tasks, new Comparator<Task>() {
                        @Override
                        public int compare(Task o1, Task o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });

                    taskName.setText(Tasks.get(0).getName());
                    taskDate.setText(Tasks.get(0).toString());
                    taskPrio.setText(Tasks.get(0).getPriority());
                } else {
                    taskView.setText(getResources().getString(R.string.task));
                    taskNumber.setText("  " + taskIndex+"  ");

                    taskName.setText("");
                    taskDate.setText("");
                    taskPrio.setText("");
                }


            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, getResources().getString(R.string.cancelTask), Toast.LENGTH_SHORT).show();
            }
        }



    }
}
package com.example.inclass06;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button thread, asyncTask;
    SeekBar seekBar;
    ProgressBar progressBar;
    TextView selectedComplexity, average, updatedValues;
    ListView listView;

    public int selectedComplexityValue ;
    public static ArrayList<Double> generatedNumbers = new ArrayList<Double>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        thread = findViewById(R.id.threadButton);
        asyncTask = findViewById(R.id.asyncTaskButton);
        seekBar = findViewById(R.id.seekBar);
        progressBar = findViewById(R.id.progressBar);
        selectedComplexity = findViewById(R.id.selectedComplexity);
        average = findViewById(R.id.averageTextView);
        updatedValues = findViewById(R.id.updatedValue);
        listView = findViewById(R.id.listView);
        selectedComplexityValue = seekBar.getProgress();
        selectedComplexity.setText(String.valueOf(selectedComplexityValue));


        final ExecutorService threadPool;

        threadPool = Executors.newFixedThreadPool(2);

        final Handler handler = new Handler(new Handler.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override

            public boolean handleMessage(@NonNull Message msg) {
                final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,generatedNumbers);
                listView.setAdapter(adapter);

                switch (msg.what){
                    case GetValueByThreading.STATUS_START:
                        adapter.notifyDataSetChanged();
                        Log.d("hey", generatedNumbers.size()+"");
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Start), Toast.LENGTH_SHORT).show();
                        average.setText("");
                        progressBar.setMin(0);
                        progressBar.setMax(selectedComplexityValue);
                        progressBar.setProgress(0);
                        updatedValues.setText(String.valueOf(0)+"/"+String.valueOf(selectedComplexityValue));
                        asyncTask.setEnabled(false);
                        thread.setEnabled(false);
                        break;
                    case GetValueByThreading.STATUS_PROGRESS:
                        Double number = (Double)msg.obj;
                        generatedNumbers.add(number);
                        adapter.notifyDataSetChanged();
                        Log.d("hey", generatedNumbers.size()+"");
                        Log.d("hey", number+"");
                        progressBar.setProgress(generatedNumbers.size());
                        average.setText(String.valueOf(calculateAll(generatedNumbers)));
                        updatedValues.setText(String.valueOf(generatedNumbers.size())+"/"+String.valueOf(selectedComplexityValue));
                        progressBar.setProgress(generatedNumbers.size());
                        break;
                     case GetValueByThreading.STATUS_FINISHED:
                         Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Finish), Toast.LENGTH_SHORT).show();
                         asyncTask.setEnabled(true);
                         thread.setEnabled(true);

                }
                return false;
            }
        });




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedComplexityValue = progress;
                selectedComplexity.setText(String.valueOf(selectedComplexityValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        thread.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override

            public void onClick(View v) {
                generatedNumbers = new ArrayList<Double>();
                if(selectedComplexityValue != 0){
                    threadPool.execute(new GetValueByThreading(handler));
//                    Thread thread = new Thread(new GetValueByThreading(handler));
//                    thread.start();
                }else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Fail), Toast.LENGTH_SHORT).show();

                }

            }
        });



        asyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatedNumbers = new ArrayList<Double>();
                if(selectedComplexityValue != 0){
                   GetValueByAsyncTask getValue = new GetValueByAsyncTask();
                   getValue.execute(selectedComplexityValue);

                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Fail), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public class GetValueByThreading implements Runnable {

        HeavyWork heavyWork = new HeavyWork();
        Handler handler;

        static final int STATUS_START = 001;
        static final int STATUS_PROGRESS = 002;
        static final int STATUS_FINISHED = 003;
        static final int STATUS_CANCELED = 004;


        public GetValueByThreading(Handler handler){
            this.handler = handler;
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {

            try{
                Message startMessage = new Message();
                startMessage.what = STATUS_START;
                handler.sendMessage(startMessage);

                int counter = 0;
                while(counter < selectedComplexityValue){
                    Message progressMessage = new Message();
                    progressMessage.what = STATUS_PROGRESS;
                    Double number = heavyWork.getNumber();
                    progressMessage.obj = number;
                    handler.sendMessage(progressMessage);
                    counter++;
                }

                Message finishedMessage = new Message();
                finishedMessage.what = STATUS_FINISHED;
                handler.sendMessage(finishedMessage);

            } catch (Exception e){
                Message canceledMessage = new Message();
                canceledMessage.what = STATUS_CANCELED;
                handler.sendMessage(canceledMessage);
            }


        }
    }



    public class GetValueByAsyncTask extends AsyncTask<Integer,Double, ArrayList<Double>>{

        HeavyWork heavyWork = new HeavyWork();
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,generatedNumbers);

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Start), Toast.LENGTH_SHORT).show();
            listView.setAdapter(arrayAdapter);
            average.setText("");
            progressBar.setMin(0);
            updatedValues.setText(String.valueOf(0)+"/"+String.valueOf(selectedComplexityValue));
            progressBar.setMax(selectedComplexityValue);
            progressBar.setProgress(0);
            asyncTask.setEnabled(false);
            thread.setEnabled(false);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            super.onPostExecute(doubles);
            Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Finish), Toast.LENGTH_SHORT).show();
            asyncTask.setEnabled(true);
            thread.setEnabled(true);

        }

        @Override
        protected void onProgressUpdate(Double... values) {
            super.onProgressUpdate(values);
            Log.d("generatedNumber", values[0]+"");
            arrayAdapter.notifyDataSetChanged();
            average.setText(String.valueOf(calculateAll(generatedNumbers)));
            updatedValues.setText(String.valueOf(generatedNumbers.size())+"/"+String.valueOf(selectedComplexityValue));
            progressBar.setProgress(generatedNumbers.size());

        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integer) {
            int counter = 0;
            while(counter < integer[0]){
                Double number = heavyWork.getNumber();
                generatedNumbers.add(number);
                publishProgress(number);
                counter++;
            }
            return generatedNumbers;
        }

    }


    public static double calculateAll(ArrayList<Double> allNumbers) {
        double average;
        double total = 0.0;
        for (Double allNumber : allNumbers) {
            total += allNumber;
        }
        average = total / allNumbers.size();
        return average;
    }


}
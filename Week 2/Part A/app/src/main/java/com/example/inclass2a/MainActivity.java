package com.example.inclass2a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView text , resultText , resultOutput;
    EditText inputNumber;
    Button toMeter,toFeet,toMiles,clearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        resultText = findViewById(R.id.resultText);
        resultOutput = findViewById(R.id.resultOutput);

        inputNumber = findViewById(R.id.inputNumber);


        text.setText(R.string.inches);
        resultText.setText(R.string.result);
        resultOutput.setText("");
        inputNumber.setHint(R.string.hint);



        toMeter = findViewById(R.id.buttonToMeter);
        toMeter.setText(R.string.meter);

        toFeet = findViewById(R.id.buttonToFeet);
        toFeet.setText(R.string.feet);

        toMiles = findViewById(R.id.buttonToMiles);
        toMiles.setText(R.string.mile);

        clearAll = findViewById(R.id.buttonClearAll);
        clearAll.setText(R.string.clear);


        toMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(inputNumber.getText().toString().length() == 0){
                     Toast.makeText(MainActivity.this,"an input is missing",Toast.LENGTH_LONG).show();
                 } else{
                     try{
                         double inputRes = Double.parseDouble(inputNumber.getText().toString());
                         double finalOutput = inputRes * 0.0254;
                         Log.d("Result",finalOutput + "");
                         resultOutput.setText(String.format("%.4f", finalOutput) + " Meters");

                     } catch (Exception e){
                         Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_LONG).show();
                         Log.d("Error","Cannot resolve");

                     }
                 }

            }
        });

        toFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputNumber.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this,"an input is missing",Toast.LENGTH_LONG).show();
                } else{
                    try{
                        double inputRes = Double.parseDouble(inputNumber.getText().toString());
                        double finalOutput = inputRes * 1 / 12;
                        Log.d("Result",finalOutput + "");
                        resultOutput.setText(String.format("%.5f", finalOutput) + " Feet");

                    } catch (Exception e){
                        Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_LONG).show();
                        Log.d("Error","Cannot resolve");

                    }
                }

            }
        });



        toMiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(inputNumber.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this,"an input is missing",Toast.LENGTH_LONG).show();
                } else{
                    try{
                        double inputRes = Double.parseDouble(inputNumber.getText().toString());
                        double finalOutput = inputRes * 1 / 63360;
                        Log.d("Result",finalOutput + "");
                        resultOutput.setText(String.format("%.9f", finalOutput) + " Miles");

                    } catch (Exception e){
                        Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_LONG).show();
                        Log.d("Error","Cannot resolve");

                    }
                }

            }
        });


        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    resultOutput.setText("");
                    inputNumber.setText("");

                } catch (Exception e){
                    Toast.makeText(MainActivity.this,"Cleared All",Toast.LENGTH_LONG).show();
                    Log.d("Error","Cannot resolve");

                }
            }
        });

    }
}
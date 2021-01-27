package com.example.inclass2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text , resultText , resultOutput;
    EditText inputNumber;
    RadioButton toMeter,toFeet,toMiles,clearAll;
    RadioGroup radioGroup;


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

        radioGroup = findViewById(R.id.radioGroup);

        toMeter = findViewById(R.id.radioButtonToMeter);
        toMeter.setText(R.string.meter);

        toFeet = findViewById(R.id.radioButtonFeet);
        toFeet.setText(R.string.feet);

        toMiles = findViewById(R.id.radioButtonToMile);
        toMiles.setText(R.string.mile);

        clearAll = findViewById(R.id.radioButtonClearAll);
        clearAll.setText(R.string.clear);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonToMeter){
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
                } else if(checkedId == R.id.radioButtonFeet){
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
                } else if (checkedId == R.id.radioButtonToMile){

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
                else {
                    try{

                        resultOutput.setText("");
                        inputNumber.setText("");

                    } catch (Exception e){
                        Toast.makeText(MainActivity.this,"Cleared All",Toast.LENGTH_LONG).show();
                        Log.d("Error","Cannot resolve");

                    }
                }
            }
        });




    }
}
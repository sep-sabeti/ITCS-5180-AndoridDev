package com.example.homework01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Instantiating objects

    EditText billValueInput;
    RadioGroup tipSelection;
    SeekBar customTip;
    TextView tipOutPut, totalOutput , progressText;
    Button exit;

    double currentTip;
    double currentSeekBartip;
    double currentTotalvalue;

    double totalValueCalculatorAndTip(){
        return this.currentTotalvalue + this.currentTotalvalue*this.currentTip/100;
    }

    void tipSetter(double tip){
        this.currentTip = tip;
    }

    double tipGetter(){
        return this.currentTip;
    }

    void currentValeSetterToZero(){
        this.currentTotalvalue = 0.0;
    }



    void currentValeSetter(double value){
        this.currentTotalvalue = value;
    }

    void updateDisplay(){
        tipOutPut.setText((int)tipGetter() + " %");
        totalOutput.setText(totalValueCalculatorAndTip() + " $");
    }

    boolean inputCheckerToast(EditText billValueInput){
        String inputName = billValueInput.getText().toString();
        if(inputName.matches("")){
            currentValeSetterToZero();
            tipOutPut.setText("");
            totalOutput.setText("");
            return true;
        } else{
            return false;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating objects from the layout
        billValueInput = findViewById(R.id.inputBillNumber);
        tipSelection = findViewById(R.id.radioGroup);
        customTip = findViewById(R.id.seekBar);
        tipOutPut = findViewById(R.id.tipValeResult);
        totalOutput = findViewById(R.id.totalValueResult);
        exit = findViewById(R.id.exit);
        progressText = findViewById(R.id.progressText);

        customTip.setEnabled(false);


        //Creating the logic
        billValueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Change",String.valueOf(s));
                if(String.valueOf(s).matches("")){
                    currentValeSetterToZero();
                    tipOutPut.setText("");
                    totalOutput.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(String.valueOf(s).matches("")){
                    currentValeSetterToZero();
                    tipOutPut.setText("");
                    totalOutput.setText("");
                } else{

                    currentValeSetter(Double.parseDouble(String.valueOf(s)));
                    updateDisplay();
                };

                }
        });

        customTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Progress started",String.valueOf(progress)) ;
                currentSeekBartip = Double.parseDouble(String.valueOf(progress));
                progressText.setText((int)currentSeekBartip + " %");
                tipSetter(currentSeekBartip);
                Log.d("current Tip",String.valueOf((int)tipGetter())) ;
                updateDisplay();
                inputCheckerToast(billValueInput);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        tipSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.tenPercent){
                    tipSetter(10.0);
                    progressText.setText("");
                    customTip.setEnabled(false);
                    tipOutPut.setText("10 %");
                    customTip.setProgress(10);
                    Log.d("Progress started", String.valueOf(tipGetter())) ;
                    totalOutput.setText(totalValueCalculatorAndTip() + " $");
                    inputCheckerToast(billValueInput);
                    if(inputCheckerToast(billValueInput)){
                        Toast.makeText(MainActivity.this,R.string.missingInput,Toast.LENGTH_SHORT).show();
                    }

                } else if(checkedId == R.id.fifteenPercent){
                    tipSetter(15.0);
                    progressText.setText("");
                    tipOutPut.setText("15 %");
                    customTip.setEnabled(false);
                    Log.d("Progress started", String.valueOf(tipGetter())) ;
                    customTip.setProgress(15);
                    totalOutput.setText(totalValueCalculatorAndTip() + " $");
                    inputCheckerToast(billValueInput);
                    if(inputCheckerToast(billValueInput)){
                        Toast.makeText(MainActivity.this,R.string.missingInput,Toast.LENGTH_SHORT).show();
                    }


                } else if(checkedId == R.id.eighteenPercent){
                    tipSetter(18.0);
                    progressText.setText("");
                    tipOutPut.setText("18 %");
                    customTip.setEnabled(false);
                    customTip.setProgress(18);
                    Log.d("Progress started", String.valueOf(tipGetter())) ;
                    totalOutput.setText(totalValueCalculatorAndTip() + " $");
                    inputCheckerToast(billValueInput);
                    if(inputCheckerToast(billValueInput)){
                        Toast.makeText(MainActivity.this,R.string.missingInput,Toast.LENGTH_SHORT).show();
                    }

                } else if(checkedId == R.id.custom){
                    customTip.setEnabled(true);
                    tipOutPut.setText(totalValueCalculatorAndTip() + "%");
                    inputCheckerToast(billValueInput);
                    if(inputCheckerToast(billValueInput)){
                        Toast.makeText(MainActivity.this,R.string.missingInput,Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }



}
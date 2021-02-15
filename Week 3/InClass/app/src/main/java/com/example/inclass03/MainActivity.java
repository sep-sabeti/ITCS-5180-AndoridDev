package com.example.inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,email,id;
    TextView department, receviedDept;
    Button select, submit;
    final public static int REQ_CODE_RADIO_BUTTON = 100;
    final public static String USER_KEY = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameOutput);
        email = findViewById(R.id.emailOutput);
        id = findViewById(R.id.idOutput);
        department = findViewById(R.id.department);
        select = findViewById(R.id.select);
        submit = findViewById(R.id.submit);
        receviedDept = findViewById(R.id.receivedDept);

        setTitle(R.string.title_reg);



        //Event listener for EditTexts in the MainActivity

        //StartActivityForResult here
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecodActivity.class);
                startActivityForResult(intent,REQ_CODE_RADIO_BUTTON);

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString() != "" && email.getText().toString() != "" && id.getText().toString() != "" && receviedDept.getText().toString() != ""){
                    User user = new User(name.getText().toString(),email.getText().toString(),id.getText().toString(), receviedDept.getText().toString());
                    Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
                    intent.putExtra(USER_KEY,user);
                    startActivity(intent);
                }
//                if(name.getText().toString() == ""){
//                    Toast.makeText(MainActivity.this,getResources().getString(R.string.MissingInfo)+" "+getResources().getString(R.string.name),Toast.LENGTH_SHORT).show();
//                }
//                if(email.getText().toString() == ""){
//                    Toast.makeText(MainActivity.this,getResources().getString(R.string.MissingInfo)+" "+getResources().getString(R.string.email),Toast.LENGTH_SHORT).show();
//                }
//                 if(id.getText().toString() == ""){
//                    Toast.makeText(MainActivity.this,getResources().getString(R.string.MissingInfo)+" "+getResources().getString(R.string.id),Toast.LENGTH_SHORT).show();
//                }
//                 if(receviedDept.getText().toString() == ""){
//                    Toast.makeText(MainActivity.this,getResources().getString(R.string.MissingInfo)+" "+getResources().getString(R.string.dep),Toast.LENGTH_SHORT).show();
//                }
                 if(name.getText().toString() == "" || email.getText().toString() == "" || id.getText().toString() == "" || receviedDept.getText().toString() == "") {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.AllMissingInfo),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_RADIO_BUTTON){

            if(resultCode == RESULT_OK){
                if(data != null && data.hasExtra(SecodActivity.ENTERED_DATA)){
                    receviedDept.setText(" " + data.getStringExtra(SecodActivity.ENTERED_DATA));
                }

            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, getResources().getString(R.string.CanceledRequest), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.missingRequrestCode), Toast.LENGTH_SHORT).show();
        }





    }
}
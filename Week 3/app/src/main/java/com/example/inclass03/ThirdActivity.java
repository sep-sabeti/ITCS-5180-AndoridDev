package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    TextView name, email,id,dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getResources().getString(R.string.title_pro));

        name = findViewById(R.id.nameOutput2);
        email = findViewById(R.id.emailOutput2);
        id = findViewById(R.id.idOutput2);
        dep = findViewById(R.id.deptOutput2);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.USER_KEY)){
            User user = (User)getIntent().getSerializableExtra(MainActivity.USER_KEY);

            name.setText(user.name);
            email.setText(user.email);
            id.setText(user.id);
            dep.setText(user.department);
        }
        else{
            Toast.makeText(this, getResources().getString(R.string.SomethingHappened), Toast.LENGTH_SHORT).show();
        }
    }
}
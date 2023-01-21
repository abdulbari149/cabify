package com.example.cabpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button log;
    Button sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log=findViewById(R.id.login);
        sign=findViewById(R.id.signup);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentlog=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intentlog);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsign=new Intent(MainActivity.this,signupactivity.class);
//
                startActivity(intentsign);
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Getting Started with DataBase").child("RealTime").setValue("yoooo");
    }

}
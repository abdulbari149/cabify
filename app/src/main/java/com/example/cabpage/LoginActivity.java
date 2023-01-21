package com.example.cabpage;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {
    ImageButton btn;
    Button lgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.button4);
        lgn=findViewById(R.id.loginbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intentstart = new Intent  (LoginActivity.this, Options.class);
                startActivity(intentstart);

    }});
    };
}
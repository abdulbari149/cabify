package com.abdulbari149.driverx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private final View.OnClickListener OnLoginBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentLogin = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intentLogin);
        }
    };

    private final View.OnClickListener OnSignupBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentSignup =new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intentSignup);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent optionsIntent = new Intent(MainActivity.this, OptionsActivity.class);
            startActivity(optionsIntent);
        }
        findViewById(R.id.login).setOnClickListener(OnLoginBtnClick);
        findViewById(R.id.signup).setOnClickListener(OnSignupBtnClick);

    }

}
package com.example.cabpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupactivity extends AppCompatActivity {
    ImageButton btn;
    TextView t1;
    EditText em;
    EditText pass;
    EditText cnf;
    Button sign;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        btn=findViewById(R.id.imageButton);
        t1=findViewById(R.id.clickhere);
        em=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        cnf=findViewById(R.id.cnfpassword);
        sign=findViewById(R.id.signupbtn);
        auth=FirebaseAuth.getInstance();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(signupactivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(signupactivity.this,LoginActivity.class));

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=em.getText().toString().trim();
                String password=pass.getText().toString().trim();
                String Confirm=cnf.getText().toString().trim();
                if (TextUtils.isEmpty(email )|| TextUtils.isEmpty(password) || TextUtils.isEmpty(Confirm)){
                    Toast.makeText(signupactivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(signupactivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(Confirm)){
                    Toast.makeText(signupactivity.this, "Passwords did not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(email,password);
                }
            }
        });

    }

    private void registerUser(String email1, String password1) {
        auth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(signupactivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(signupactivity.this, "Registering new user", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(signupactivity.this,Options.class));
                }
                else {
                    Toast.makeText(signupactivity.this, "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
package com.example.cabify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {
    ImageButton backButton;
    TextView clickHere;
    EditText emailField;
    EditText passwordField;
    EditText confirmPasswordField;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        backButton = findViewById(R.id.backBtn);
        clickHere=findViewById(R.id.clickhere);
        emailField =findViewById(R.id.email);
        passwordField=findViewById(R.id.password);
        confirmPasswordField=findViewById(R.id.cnfpassword);
        signUpButton=findViewById(R.id.signupbtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

            }
        });
        signUpButton.setOnClickListener(this.signUpHandler);

    }

    private final View.OnClickListener signUpHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email= emailField.getText().toString().trim();
            String password= passwordField.getText().toString().trim();
            String confirmPassword=confirmPasswordField.getText().toString().trim();
            if (TextUtils.isEmpty(email )|| TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                Toast.makeText(SignUpActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<6){
                Toast.makeText(SignUpActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
            }
            else if(!password.equals(confirmPassword)){
                Toast.makeText(SignUpActivity.this, "Passwords did not match", Toast.LENGTH_SHORT).show();
            }
            else{
                registerUser(email,password);
            }
        }
    };

    private final OnSuccessListener<AuthResult> registerUserSuccess = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Toast.makeText(SignUpActivity.this, "Congratulations, You are successfully registered", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(SignUpActivity.this, OptionsActivity.class);
            startActivity(homeIntent);
        }
    };

    private final OnFailureListener registerUserFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            if (e instanceof FirebaseAuthUserCollisionException) {
                Log.d("Firebase Error: ", "user already exists....");
                Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        }
    };

    private void registerUser(@NonNull String email, @NonNull String password) {
        Log.d("USER EMAIL:", email);
        Log.d("USER PASSWORD", password);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(SignUpActivity.this, this.registerUserSuccess)
                .addOnFailureListener(SignUpActivity.this, this.registerUserFailure);

    };

}
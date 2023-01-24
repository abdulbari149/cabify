package com.example.cabify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;


    private final View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText emailField = findViewById(R.id.loginEmail);
            EditText passwordField = findViewById(R.id.loginPassword);
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            }
            loginUser(email, password);
        }
    };

    private final View.OnClickListener backBtnHandler = view -> onBackPressed();


    private final View.OnClickListener googleHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginBackBtn).setOnClickListener(this.backBtnHandler);
        findViewById(R.id.loginBtn).setOnClickListener(this.loginHandler);
        findViewById(R.id.google).setOnClickListener(this.googleHandler);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


    }




    private final OnSuccessListener<AuthResult> loginUserSuccessHandler = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Log.d("auth login:", authResult.getUser().toString());
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, OptionsActivity.class));
        };
    };

    private final OnFailureListener loginUserFailureHandler = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(LoginActivity.this, "Not working", Toast.LENGTH_LONG).show();
        };
    };

    private void loginUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(LoginActivity.this, this.loginUserSuccessHandler)
                .addOnFailureListener(LoginActivity.this, this.loginUserFailureHandler);
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Toast.makeText(this, "Google Sign in Successfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, OptionsActivity.class));
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}




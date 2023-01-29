package com.abdulbari149.driverx;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginBackBtn).setOnClickListener(this.backBtnHandler);
        findViewById(R.id.loginBtn).setOnClickListener(this.loginHandler);
        findViewById(R.id.google).setOnClickListener(this.googleHandler);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

    }


    private final ActivityResultLauncher<Intent> googleSigInActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        googleFirebaseSignIn(data);
                    } else {
                        Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText emailField = findViewById(R.id.loginEmail);
            EditText passwordField = findViewById(R.id.loginPassword);
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                return;
            }
            loginUser(email, password);
        }
    };

    private final View.OnClickListener backBtnHandler = view -> onBackPressed();


    private final View.OnClickListener googleHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("GOOGLE HANDLER: ", "CLICKED!!!!");
            Intent intent = googleSignInClient.getSignInIntent();
            googleSigInActivityLauncher.launch(intent);
        }
    };


    private void loginUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(LoginActivity.this, this.loginUserSuccessHandler)
                .addOnFailureListener(LoginActivity.this, this.loginUserFailureHandler);
    };

    private final OnSuccessListener<AuthResult> loginUserSuccessHandler = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
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

    private void googleFirebaseSignIn(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            String idToken = account.getIdToken();
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            FirebaseAuth.getInstance()
                    .signInWithCredential(firebaseCredential)
                    .addOnSuccessListener(LoginActivity.this, googleFirebaseSignInSuccess)
                    .addOnFailureListener(LoginActivity.this, googleFirebaseSignInFailure);
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private final OnSuccessListener<AuthResult> googleFirebaseSignInSuccess = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Toast.makeText(LoginActivity.this, "Google Sign in Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, OptionsActivity.class));
        }
    };

    private final OnFailureListener googleFirebaseSignInFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}




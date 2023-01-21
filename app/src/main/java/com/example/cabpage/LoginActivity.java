package com.example.cabpage;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private String AppID="720315182820931";
    TextView txt1;
    ImageButton btn;
    Button lgn;
    EditText emaillgn;
    EditText passlgn;
    LoginButton loginButton;
    ImageButton google;
    private FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    private CallbackManager mcallbackManager;
   private static final String TAG="FacebookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.button4);
        txt1=findViewById(R.id.textuser);
        lgn=findViewById(R.id.loginbtn);
        emaillgn=findViewById(R.id.logemail);
        passlgn=findViewById(R.id.logpassword);
        loginButton=findViewById(R.id.login_button);
        google=findViewById(R.id.googlebtn);
        auth=FirebaseAuth.getInstance();
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mcallbackManager=CallbackManager.Factory.create();
        loginButton.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"onsuccess"+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emaillgn.getText().toString().trim();
                String password=passlgn.getText().toString().trim();
                if (TextUtils.isEmpty(email )|| TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                loginuser(email,password);

    }});
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });
    }

    private void loginuser(String useremail, String userpassword) {
        auth.signInWithEmailAndPassword(useremail, userpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, Options.class));
                finish();
            }
        });

    }

    private void Signin(){
        Intent intent=googleSignInClient.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                startActivity(new Intent(LoginActivity.this,Options.class));
            }
            catch (ApiException e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void handleFacebookToken(AccessToken token){
        Log.d(TAG,"handleFacebooktoken"+ token);
        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"sign in with credentials successfully");
                    FirebaseUser user=auth.getCurrentUser();
                    updateUI(user);
                }
                else{

                }

            }
        });
    }

    private void updateUI(FirebaseUser user){
        if(user !=null){
            txt1.setText(user.getDisplayName());
            if(user.getPhotoUrl()!= null){
                String photoUrl=user.getPhotoUrl().toString();
                photoUrl=photoUrl+"?g"
            }

        }
    }





}
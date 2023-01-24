package com.example.cabify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionsActivity extends AppCompatActivity  {
    ImageButton carbutton;
    ImageButton carprebutton;
    ImageButton autobutton;
    ImageButton bikebutton;
    PopupMenu popup_menu;
    View view;
    ImageView dp;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ImageView menuButton = findViewById(R.id.menu_button);
        carbutton = findViewById(R.id.carbtn);
        carprebutton = findViewById(R.id.imageButton4);
        autobutton = findViewById(R.id.imageButton6);
        bikebutton = findViewById(R.id.imageButton7);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);




        carbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car_select = new Intent(OptionsActivity.this,Car.class);
                startActivity(car_select);
            }
        });
        carprebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car_pre_select = new Intent(OptionsActivity.this,CarPre.class);
                startActivity(car_pre_select);
            }
        });
        autobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent auto_select = new Intent(OptionsActivity.this,Auto.class);
                startActivity(auto_select);
            }
        });
        bikebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bike_select = new Intent(OptionsActivity.this,RiderListView.class);
                startActivity(bike_select);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(OptionsActivity.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_item_1) {
                            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                    Toast.makeText(OptionsActivity.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OptionsActivity.this,LoginActivity.class));
                                }
                            });
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
//        AccessToken accessToken=AccessToken.getCurrentAccessToken();
//        dp=findViewById(R.id.display);
//
//        GraphRequest request = GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        try{
//                            String fullname = object.getString("name");
//                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
//                            Toast.makeText(Options.this, "logged in as"+ fullname, Toast.LENGTH_SHORT).show();
//                            Picasso.get().load(url).into(dp);
//
//                        }
//                        catch (JSONException e){
//                            e.printStackTrace();
//
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,picture.type(large)");
//        request.setParameters(parameters);
//        request.executeAsync();

    }
}
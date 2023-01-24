package com.example.cabify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
    PopupMenu popup_menu;
    View view;
    ImageView dp;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;


    private void startVehicleActivityOnClick(int id, String vehicleType) {
        findViewById(id).setOnClickListener(
                v -> {
                    Intent vehicleIntent = new Intent(OptionsActivity.this, VehicleActivity.class);
                    vehicleIntent.putExtra("vehicle_type", vehicleType);
                    startActivity(vehicleIntent);
                }
        );
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ImageView menuButton = findViewById(R.id.menu_button);
        startVehicleActivityOnClick(R.id.carBtn, "car");
        startVehicleActivityOnClick(R.id.carPreBtn, "carPre");
        startVehicleActivityOnClick(R.id.autoBtn, "auto");
        startVehicleActivityOnClick(R.id.bikeBtn, "bike");

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
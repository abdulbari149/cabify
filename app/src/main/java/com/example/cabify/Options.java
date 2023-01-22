package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Options extends AppCompatActivity  {
    ImageButton carbutton;
    ImageButton carprebutton;
    ImageButton autobutton;
    ImageButton bikebutton;
    PopupMenu popup_menu;
    View view;


    Button logout;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ImageView menuButton = findViewById(R.id.menu_button);
        carbutton = findViewById(R.id.carbtn);
        carprebutton = findViewById(R.id.imageButton4);
        autobutton = findViewById(R.id.imageButton6);
        bikebutton = findViewById(R.id.imageButton7);



        carbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car_select = new Intent(Options.this,Car.class);
                startActivity(car_select);
            }
        });
        carprebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car_pre_select = new Intent(Options.this,CarPre.class);
                startActivity(car_pre_select);
            }
        });
        autobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent auto_select = new Intent(Options.this,Auto.class);
                startActivity(auto_select);
            }
        });
        bikebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bike_select = new Intent(Options.this,Bike.class);
                startActivity(bike_select);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(Options.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_1:
                                // Do something for menu item 1
                                return true;
                            case R.id.menu_item_2:
                                Intent logout = new Intent(Options.this,LoginActivity.class);
                                startActivity(logout);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
//        AccessToken accessToken=AccessToken.getCurrentAccessToken();
//        logout=findViewById(R.id.logoutbtn);
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
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginManager.getInstance().logOut();
//                startActivity(new Intent(Options.this, SignUpActivity.class));
//                finish();
//            }
//        });

    }
}
package com.example.cabpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toolbar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.navigation.NavigationView;

public class Options extends AppCompatActivity  {
    ImageView menubutton;
    PopupMenu popup_menu;
    View view;

    Button logout;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        menubutton = findViewById(R.id.menu_button);ImageView menuButton = findViewById(R.id.menu_button);
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
                                // Do something for menu item 2
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        logout=findViewById(R.id.logoutbtn);
        dp=findViewById(R.id.display);

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try{
                            String fullname=object.getString("name");
                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Toast.makeText(Options.this, "logged in as"+ fullname, Toast.LENGTH_SHORT).show();
                            Picasso.get().load(url).into(dp);

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(Options.this,signupactivity.class));
                finish();
            }
        });

    }
}
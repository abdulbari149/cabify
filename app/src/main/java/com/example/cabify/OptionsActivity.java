package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class OptionsActivity extends AppCompatActivity  {
    PopupMenu popup_menu;
    View view;


    Button logout;  
    ImageView dp;

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
                        switch (item.getItemId()) {
                            case R.id.menu_item_1:
                                // Do something for menu item 1
                                return true;
                            case R.id.menu_item_2:
                                Intent logout = new Intent(OptionsActivity.this,LoginActivity.class);
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